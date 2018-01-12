package com.gxut.ui.commonui.tree;


import java.util.ArrayList;
import java.util.List;

/**
 * 节点处理帮助类，用于计算等工作
 * Created by Bitliker on 2017/2/27.
 */

public class TreeHelper {


    /**
     * 根据所有节点获取可见节点
     *
     * @param allNodes
     * @return
     */
    public static List<Node> getVisibleNode(List<Node> allNodes) {
        List<Node> visibleNodes = new ArrayList<Node>();
        for (Node node : allNodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                visibleNodes.add(node);
            }
        }
        return visibleNodes;
    }

    /**
     * 1.根据用户自定义的数据源列表，转变成对应的节点数据
     * 2.排序
     * TODO  1.本方法违反单一原则，处理过多事物   2.使用处理数据算法需要优化、优化
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T extends BaseNodeBean> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel)
            throws IllegalAccessException, IllegalArgumentException {
        /*排序后的节点列表*/
        List<Node> sortedNodes = new ArrayList<>();
        // 将用户数据转化为List<Node>
        List<Node> nodes = convertData2Nodes(datas);
        // 拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        //排序以及设置Node间关系
        for (Node node : rootNodes) {
            addNode(sortedNodes, node, defaultExpandLevel, 1);
        }
        return sortedNodes;
    }


    public static List<Node> getClickedNodes(List<Node> datas) {
        List<Node> clickNodes = new ArrayList<>();

        if (datas==null||datas.isEmpty()) return clickNodes;
        for (Node t : datas) {
            if (t.isChecked())
                clickNodes.add(t);
        }
        return clickNodes;
    }

    /**
     * 将泛型datas转换为node
     *
     * @param datas
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T extends BaseNodeBean> List<Node> convertData2Nodes(List<T> datas)
            throws IllegalAccessException, IllegalArgumentException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        int id, pId;
        String title, subTitle;
        for (BaseNodeBean t : datas) {
            id = t.getId();
            pId = t.getPid();
            title = t.getTitle();
            subTitle = t.getSubTitle();
            node = new Node();
            node.setId(id);
            node.setpId(pId);
            node.setTitle(title);
            node.setSubTitle(subTitle);
            node.setT(t);
            nodes.add(node);
        }
        setNodeHier(nodes);
        return nodes;
    }

    /**
     * 设置节点层次关系
     *
     * @param nodes
     */
    private static void setNodeHier(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node ni = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node nj = nodes.get(j);
                if (ni.getId() == nj.getpId()) {
                    ni.getChildrenNodes().add(nj);
                    nj.setParent(ni);
                } else if (ni.getpId() == nj.getId()) {
                    ni.setParent(nj);
                    nj.getChildrenNodes().add(ni);
                }
            }
        }
    }

    /**
     * 获取数据的根节点，注意，为减少计算，这里默认根节点的 pid==0
     *
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> roots = new ArrayList<>();
        if (!(nodes==null||nodes.isEmpty()))
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).getpId() == 0) {
                    nodes.get(i).setLevel(0);
                    roots.add(nodes.get(i));
                    nodes.remove(i);
                    i--;
                }
            }
        return roots;
    }


    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static void addNode(List<Node> nodes, Node node, int defaultExpandLeval, int currentLevel) {
        node.setLevel(currentLevel);
        nodes.add(node);
        if (defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildrenNodes().size(); i++) {
            addNode(nodes, node.getChildrenNodes().get(i), defaultExpandLeval,
                    currentLevel + 1);
        }
    }

    public static void setNodeChecked(Node node, boolean isChecked) {
        setChildrenNodeChecked(node, isChecked);
        setParentNodeChecked(node, isChecked);
    }

    /**
     * 设置父节点选择
     *
     * @param node
     */
    private static void setParentNodeChecked(Node node, boolean isChecked) {
        if (!node.isRoot()) {
            Node rootNode = node.getParent();
            boolean isAllChecked = true;
            for (Node n : rootNode.getChildrenNodes()) {
                if ((isChecked && !n.isChecked()) || (!isChecked && n.isChecked())) {
                    isAllChecked = false;
                    break;
                }
            }
            if (isAllChecked) {
                rootNode.setChecked(isChecked);
            }
            setParentNodeChecked(rootNode, isChecked);
        }
    }

    /**
     * 非叶子节点,子节点处理
     */
    private static void setChildrenNodeChecked(Node node, boolean isChecked) {
        node.setChecked(isChecked);
        if (!node.isLeaf()) {
            for (Node n : node.getChildrenNodes()) {
                // 所有子节点设置是否选择
                setChildrenNodeChecked(n, isChecked);
            }
        }
    }
}
