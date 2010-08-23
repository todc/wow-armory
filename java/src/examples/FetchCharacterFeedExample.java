import java.util.List;

import com.todc.wgrarmory.Armory;
import com.todc.wgrarmory.DefaultArmoryImpl;
import com.todc.wgrarmory.model.FeedEntry;
import com.todc.wgrarmory.model.FeedFilter;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FetchCharacterFeedExample {

    public static void main(String... args) throws Exception {

        // Instantiate the Armory class
        Armory armory = new DefaultArmoryImpl();

        // Create a feed filter
        FeedFilter filter = new FeedFilter();
        filter.setFilters(new String[] {
            FeedFilter.BOSSKILL, FeedFilter.LOOT, FeedFilter.ACHIEVEMENT
        });
        filter.setAchCategories(new Integer[] {
            FeedFilter.ACH_DUNGEONS
        });
        filter.setItemQuality(FeedFilter.EPIC);
        filter.setItemLevel(200);

        // send the request to the armory
        List<FeedEntry> entries = armory.fetchCharacterFeed("Gogan", "Dawnbringer", "US", filter);

        // display results
        for (FeedEntry entry : entries) {
            System.out.println(
                entry.getPublished() + " - " +
                entry.getTitle() + " - " +
                entry.getContent()
            );
        }

    }

}
