/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */


import java.util.List;

import com.todc.wgrarmory.Armory;
import com.todc.wgrarmory.DefaultArmoryImpl;
import com.todc.wgrarmory.model.Item;
import com.todc.wgrarmory.model.ItemFilter;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FetchItemsExample {

    public static void main(String... args) throws Exception {
        Armory armory = new DefaultArmoryImpl();

        ItemFilter filter = new ItemFilter();
        filter.setSource(ItemFilter.SOURCE_DUNGEON);
        filter.setItemType("weapons");

        // There is no getter/setter in the API to specify a particular dungeon
        // to filter by, however, we can specify any armory field/value using
        // addOtherCriteria if we know the specific field names and values.
        filter.addOtherCriteria("fl[dungeon]", "icecrowncitadel10");

        // get the items
        List<Item> items = armory.fetchItems(filter);

        System.out.println("Items (" + items.size() + ")");
        System.out.println("------------");
        for (Item item : items) {
            System.out.println(item.getId() + " - " + item.getItemLevel() + " - " + item.getName());
        }
    }

}
