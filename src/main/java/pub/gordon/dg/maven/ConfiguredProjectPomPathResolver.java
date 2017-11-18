package pub.gordon.dg.maven;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gitlab.api.models.GitlabProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.bean.RepositoryConfig;
import pub.gordon.dg.exception.GitRepoLocateException;
import pub.gordon.dg.exception.POMNotFoundException;
import pub.gordon.dg.git.TagHelper;
import pub.gordon.dg.gitlab.RepoInfoCache;
import pub.gordon.dg.util.GitUtil;
import pub.gordon.dg.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author Gordon
 * @date 2017-11-16 23:49
 */
public class ConfiguredProjectPomPathResolver extends ProjectPomLocalPathResolver {

    private static RepositoryConfig config = RepositoryConfig.getInstance();
    private static int DEEP = 2;
    private static TagHelper tagHelper = new TagHelper(config.getRule().getTagPattern(), config.getRule().getTagOrder());
    private static final Logger logger = LoggerFactory.getLogger(ConfiguredProjectPomPathResolver.class);
    private ModuleParentNameResolver moduleParentNameResolver;

    {
        ServiceLoader<ModuleParentNameResolver> serviceLoader = ServiceLoader.load(ModuleParentNameResolver.class);
        Iterator<ModuleParentNameResolver> iterator =  serviceLoader.iterator();
        if(iterator.hasNext()){
            moduleParentNameResolver = iterator.next();
        }else{
            logger.error("Failed to load " + ModuleParentNameResolver.class.getName() + " instance");
            System.exit(1);
        }
    }

    public List<String> resolve(String projectName) throws POMNotFoundException, IOException, GitAPIException, InterruptedException {
        String path = UrlUtil.concat(config.getDir(), projectName);
        File file = new File(path);
        if (file.getParentFile() == null || !file.getParentFile().exists()) {
            new File(file.getParent()).mkdirs();
        }
        if (!file.exists()) {
            try {
                GitlabProject project = RepoInfoCache.getProjectByName(projectName);
                if (project == null) {
                    project = tryParent(projectName);
                    if (project == null) {
                        throw new GitRepoLocateException(MessageFormat.format("Could not find repository for {0}", projectName));
                    }
                }
                GitUtil.cloneRepo(project.getHttpUrl(), file);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new POMNotFoundException(MessageFormat.format("Directory {0} does not exists.", path));
            }
        }
        if (!file.isDirectory()) throw new POMNotFoundException(MessageFormat.format("{0} is not a directory", path));
        tagHelper.checkoutToNewestTag(file);
        int deep = 0;
        List<File>[] fileQueueList = new List[DEEP];
        for (int i = 0; i < DEEP; i++) {
            fileQueueList[i] = new LinkedList<>();
        }
        fileQueueList[0].addAll(Arrays.asList(file.listFiles()));
        while (++deep < DEEP) {
            for (File f : fileQueueList[deep - 1]) {
                if (f.isDirectory()) {
                    fileQueueList[deep].addAll(Arrays.asList(f.listFiles()));
                }
            }
        }
        List<String> result = new LinkedList<String>();
        for (List<File> fs : fileQueueList) {
            if (CollectionUtils.isEmpty(fs)) continue;
            for (File f : fs) {
                if ("pom.xml".equalsIgnoreCase(f.getName()))
                    result.add(f.getAbsolutePath());
            }
        }
        return result;
    }

    private GitlabProject tryParent(String moduleName) {
        return moduleParentNameResolver.findParentProject(moduleName);
    }
}
