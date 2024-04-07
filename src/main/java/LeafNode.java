import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LeafNode<T> extends AbstractTreeNode<T>{

  public LeafNode(T data){
    super(data);
  }

  @Override
  public ITreeNode<T> addChild(Predicate<T> identifier, ITreeNode<T> child) {
    if (identifier.test(data)) {
      // promote this to a group tree node
      TreeNode<T> newNode = new TreeNode<T>(this.data);
      newNode.addChild(identifier,child);
      return newNode;
    }
    return this;
  }

  @Override
  public List<T> toList() {
    List<T> result = new ArrayList<T>();
    result.add(this.data);
    return result;
  }
}
