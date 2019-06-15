package model.utils;

import java.util.ArrayList;
import java.util.List;

public class BuildTree {

    /**
     * 构建树
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> Tree<T> build(List<Tree<T>> nodes) {

        if(nodes == null){
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

        for (Tree<T> children : nodes) {

            String pid = children.getParent_itemtypeid();
            if (pid == null || "".equals(pid) || pid.equals("0")) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setParent(true);
                    parent.setChildren(true);
                    parent.setAttributes(parent.getAttributes());
                    continue;
                }
            }

        }

        Tree<T> root = new Tree<T>();
        if (topNodes.size() == 1) {
            root = topNodes.get(0);
        }

        return root;
    }


}
