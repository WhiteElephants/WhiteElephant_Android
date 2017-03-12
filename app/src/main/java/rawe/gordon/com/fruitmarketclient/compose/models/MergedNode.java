package rawe.gordon.com.fruitmarketclient.compose.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gordon on 16/9/1.
 */
public class MergedNode {
    private int type;
    private List<String> imagePaths;
    private String content;
    private String storagePath;
    private boolean isTitle = false;
    private boolean expanded = false;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public static List<MergedNode> toMergedNodes(List<Node> nodes) {
        List<MergedNode> retValues = new ArrayList<>();
        for (Node node : nodes) {
            MergedNode mergedNode = new MergedNode();
            mergedNode.setType(node.getType());
            switch (node.getType()) {
                case NodeType.HEADER:
                    HeaderNode headerNode = (HeaderNode) node;
                    mergedNode.setContent(headerNode.getContent());
                    break;
                case NodeType.TEXT:
                    TextNode textNode = (TextNode) node;
                    if (TextUtils.isEmpty(textNode.getContent())) continue;
                    mergedNode.setContent(textNode.getContent());
                    mergedNode.setTitle(textNode.isSubTitle());
                    break;
                case NodeType.IMAGE:
                    ImageNode imageNode = (ImageNode) node;
                    mergedNode.setContent(imageNode.getContent());
                    mergedNode.setStoragePath(imageNode.getStoragePath());
                    mergedNode.setExpanded(imageNode.isExpanded());
                    break;
                case NodeType.VIDEO:
                    VideoNode videoNode = (VideoNode) node;
                    mergedNode.setContent(videoNode.getContent());
                    mergedNode.setExpanded(videoNode.isExpanded());
                    break;
                case NodeType.GROUP:
                    GroupNode groupNode = (GroupNode) node;
                    mergedNode.setContent(groupNode.getContent());
                    mergedNode.setExpanded(groupNode.isExpanded());
                    List<String> imageUrls = new ArrayList<>();
                    for (ImageNode tmp : groupNode.getImageNodes()) {
                        imageUrls.add(tmp.getStoragePath());
                    }
                    mergedNode.setImagePaths(imageUrls);
                    break;
                case NodeType.FOOTER:
                    break;
            }
            retValues.add(mergedNode);
        }
        return retValues;
    }

    public static List<Node> toNodes(List<MergedNode> mergedNodes) {
        List<Node> retValues = new ArrayList<>();
        for (MergedNode mergedNode : mergedNodes) {
            switch (mergedNode.getType()) {
                case NodeType.HEADER:
                    HeaderNode headerNode = new HeaderNode();
                    headerNode.setContent(mergedNode.getContent());
                    retValues.add(headerNode);
                    break;
                case NodeType.TEXT:
                    TextNode textNode = new TextNode();
                    textNode.setSubTitle(mergedNode.isTitle());
                    textNode.setContent(mergedNode.getContent());
                    retValues.add(textNode);
                    break;
                case NodeType.IMAGE:
                    ImageNode imageNode = new ImageNode();
                    imageNode.setExpanded(mergedNode.isExpanded());
                    imageNode.setContent(mergedNode.getContent());
                    imageNode.setStoragePath(mergedNode.getStoragePath());
                    retValues.add(imageNode);
                    break;
                case NodeType.VIDEO:
                    VideoNode videoNode = new VideoNode();
                    videoNode.setContent(mergedNode.getContent());
                    videoNode.setExpanded(mergedNode.isExpanded());
                    retValues.add(videoNode);
                    break;
                case NodeType.GROUP:
                    GroupNode groupNode = new GroupNode();
                    groupNode.setContent(mergedNode.getContent());
                    groupNode.setExpanded(mergedNode.isExpanded());
                    List<ImageNode> imageNodes = new ArrayList<>();
                    for (String tmp : mergedNode.getImagePaths()) {
                        imageNodes.add(new ImageNode(tmp));
                    }
                    groupNode.setImageNodes(imageNodes);
                    retValues.add(groupNode);
                    break;
                case NodeType.FOOTER:
                    retValues.add(new FooterNode());
                    break;
            }
        }
        return retValues;
    }
}
