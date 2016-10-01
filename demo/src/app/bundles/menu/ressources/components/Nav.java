package app.bundles.menu.ressources.components;

import java.util.ArrayList;
import java.util.Arrays;

public class Nav extends ArrayList<NavListItem> {

    /**    */
    private static final long serialVersionUID = 1L;

    public Nav() {
        super();
    }

    public Nav(NavListItem... items) {
        super();
        if (items != null && items.length > 0) {
            this.addAll(Arrays.asList(items));
        }
    }
}
