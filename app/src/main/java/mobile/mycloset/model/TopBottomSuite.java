package mobile.mycloset.model;

import java.io.Serializable;
import android.util.Log;

public class TopBottomSuite implements Serializable {
    public Top top;
    public Bottom bottom;
    public Bag bag;
    public Shoe shoe;

    public boolean isValid() {
        boolean success = top != null && bottom != null && bag != null && shoe != null;
        if (bottom == null) {
            Log.i("is not valid", "false");
        }
        return success;
    }

    public boolean isEqual(TopBottomSuite suite) {
        return top.isEqual(suite.top) &&
                bottom.isEqual(suite.bottom) &&
                bag.isEqual(suite.bag) &&
                shoe.isEqual(suite.shoe);
    }
}
