package mobile.mycloset.model;

/**
 * Created by xrz on 5/15/16.
 */
public class TopBottomSuite {
    public Top top;
    public Bottom bottom;
    public Bag bag;
    public Shoe shoe;

    public boolean isValid() {
        return top != null && bottom != null && bag != null && shoe != null;
    }

    public boolean isEqual(TopBottomSuite suite) {
        return top.isEqual(suite.top) &&
                bottom.isEqual(suite.bottom) &&
                bag.isEqual(suite.bag) &&
                shoe.isEqual(suite.shoe);
    }
}
