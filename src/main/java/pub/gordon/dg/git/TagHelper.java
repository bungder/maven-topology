package pub.gordon.dg.git;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.gordon.dg.bean.Order;
import pub.gordon.dg.bean.RepositoryConfig;
import pub.gordon.dg.util.RegUtil;
import pub.gordon.dg.util.ScriptCaller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Gordon
 * @date 2017-11-17 09:57
 */
public class TagHelper {

    private static final Logger logger = LoggerFactory.getLogger(TagHelper.class);

    private String tagPattern;
    private Order tagOrder;
    private static RepositoryConfig.GitlabConfig config = RepositoryConfig.getInstance().getGitlab();
    private static CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
            config.getUsername(), config.getPassword());

    /**
     * Constructor
     *
     * @param tagPattern regexp to match tag names
     * @param tagOrder   in which way to sort tags that match {@code tagPattern}
     */
    public TagHelper(String tagPattern, Order tagOrder) {
        this.tagPattern = tagPattern;
        this.tagOrder = tagOrder;
    }

    /**
     * Checkout code version of repository that specified by {@repoDir} to the newest tag.<br/>
     * <p>
     * The newest tag is defined as:
     * 1. find out tags that match {@code tagPattern}
     * 2. sort those tags in the order of {@code tagOrder}
     * 3. the first tag is regard as the newest tag
     *
     * @param git Git instance
     * @throws IOException
     * @throws GitAPIException
     * @throws InterruptedException
     */
    public void checkoutToNewestTag(Git git) throws IOException, GitAPIException, InterruptedException {
        File repoDir = git.getRepository().getDirectory().getParentFile();
        logger.info("Checkout {} to newest tag", repoDir.getName());

        List<Ref> tags = git.tagList().call();
        if (!CollectionUtils.isEmpty(tags)) {
            deleteTagsByCommand(repoDir);
        }
        git.fetch().setCredentialsProvider(credentialsProvider).call();
        tags = git.tagList().call();
        if (CollectionUtils.isEmpty(tags)) {
            return;
        }
        Pattern pattern = Pattern.compile(tagPattern);
        List<Ref> needTags = tags.stream().filter(t -> RegUtil.match(trimTagName(t.getName()), pattern)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needTags)) {
            return;
        }
        Collections.sort(needTags, (o1, o2) -> (tagOrder == Order.DESC ? 1 : -1) * o2.getName().compareTo(o1.getName()));
        Ref tag = needTags.get(0);

        git.checkout().setName(tag.getObjectId().getName()).call();
        logger.info("Checkout repository {} to tag {}", repoDir.getName(), trimTagName(tag.getName()));
    }

    /**
     * Using git command and linux command to batch delete tags.<br/>
     * <i>This way is fast.</i>
     *
     * @param repoDir
     * @throws IOException
     * @throws InterruptedException
     */
    private void deleteTagsByCommand(File repoDir) throws IOException, InterruptedException {
        ScriptCaller.exeCommand(repoDir.getAbsolutePath(), "git tag -l | xargs git tag -d");
    }

    /**
     * Using JGit api to batch delete tags.<br/>
     * <i>This way is too slow</i>
     *
     * @param git
     * @param tags
     * @throws GitAPIException
     */
    private void deleteTagsByAPI(Git git, List<Ref> tags) throws GitAPIException {
        git.tagDelete().setTags(toArray(tags.parallelStream().map(Ref::getName).collect(Collectors.toList()))).call();
    }

    private String[] toArray(List<String> strings) {
        if (CollectionUtils.isEmpty(strings)) {
            return null;
        }
        String[] arr = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            arr[i] = strings.get(i);
        }
        return arr;
    }

    /**
     * remove prefix "ref/tag/"
     *
     * @param refName original tag name, goes like "ref/tag/this_is_real_tag_name"
     * @return real tag name(as-is)
     */
    private String trimTagName(String refName) {
        if (StringUtils.isBlank(refName)) {
            return refName;
        }
        int index = refName.lastIndexOf('/');
        if (index < 0) {
            return refName;
        }
        String s = refName.substring(index + 1, refName.length());
        return s;
    }

}
