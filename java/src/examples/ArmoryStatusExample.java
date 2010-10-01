import java.util.List;

import com.todc.wgrarmory.Armory;
import com.todc.wgrarmory.DefaultArmoryImpl;
import com.todc.wgrarmory.model.Guild;
import com.todc.wgrarmory.model.PlayerCharacter;


/**
 * @author <a href="tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArmoryStatusExample {

    public static void main(String... args) throws Exception {

        Armory armory = new DefaultArmoryImpl();

        String[] regions = new String[] { "US", "EU", "KR", "TW" };
        for (String region : regions) {
            boolean status = armory.isArmoryUp(region);
            System.out.println(region + ": " + (status ? "up" : "down"));
        }

    }

}
