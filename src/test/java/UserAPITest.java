import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserAPITest extends UserAPI{

    @Test
    public void TC08(){
        int userid =1;
        response=getalbums(userid);

        Assert.assertEquals(response.getStatusCode(),200);

        List<Integer> idlist= response.jsonPath().get("userId");
        for(int i:idlist){
            Assert.assertEquals(i,userid);
        }
    }
}
