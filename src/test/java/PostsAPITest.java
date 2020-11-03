import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class PostsAPITest extends PostAPI{

    //Create a post
    @Test(dataProvider = "PostsData")
    public void TC01(String title, String body, int userid){
        createPosts(title,body,userid);

        Assert.assertEquals(response.getStatusCode(),201);

        Assert.assertNotNull(response.path("id"));
        Assert.assertEquals(response.path("title"),title);
        Assert.assertEquals(response.path("body"),body);
        Assert.assertEquals(response.path("userId"),userid);
    }

    @DataProvider(name="PostsData")
    public Object[][] getData(){
        return new Object[][] {{"foo","bar",1}};
    }

    /*Create posts with invalid data
        Assumptions: title,body,userID are mandatory fields
                     Maximum length of title is 30 charactors
                     Maximum length of bosy is 60 charactors
     */
    @Test(dataProvider = "PostsInvalidData")
    public void TC011(String title, String body, int userid, int statuscode){
        createPosts(title,body,userid);

        Assert.assertEquals(response.getStatusCode(),statuscode);
    }

    @DataProvider(name="PostsInvalidData")
    public Object[][] getInvalidData(){
        return new Object[][] {{"","bar",1,400},{"","abc",2,400},{"abc","cde",3,400},{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","cde",3,500},{"abc","cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",3,500},{"abc","cde","abc",500}};
    }


    //Update a post
    @Test(dataProvider = "PostsUpdateData")
    public void TC02(int id,String title, String body,int userid){
        response=updatePosts(id,title,body,userid);

        Assert.assertEquals(response.getStatusCode(),200);

        Assert.assertEquals(response.path("id"),id);
        Assert.assertEquals(response.path("title"),title);
        Assert.assertEquals(response.path("body"),body);
        Assert.assertEquals(response.path("userId"),userid);

    }
    @DataProvider(name="PostsUpdateData")
    public Object[][] getUpdateData(){
        return new Object[][] {{1,"foo","bar",2},{2,"","",2},{2,"foo","",2},{2,"","foo",3}};
    }



    //Filter a post
    @Test
    public void TC03(){
        int userid =1;
        response=filterPosts(userid);

        Assert.assertEquals(response.getStatusCode(),200);

        List<Integer> idlist= response.jsonPath().get("userId");
            for(int i:idlist){
                Assert.assertEquals(i,userid);
        }
    }

    //Delete a post
    @Test
    public void TC04(){
        int userid =1;
        response=deletePosts(userid);

        Assert.assertEquals(response.getStatusCode(),200);
    }

    //List All Resource
    @Test
    public void TC05(){
        response=listAllPosts();

        Assert.assertEquals(response.getStatusCode(),200);
    }

    //Update resource with PATCH
    @Test
    public void TC06(){
        int userid =1;
        response=updatePostsWithPatch(userid);

        Assert.assertEquals(response.getStatusCode(),200);
    }

    //ListNestedResource
    @Test
    public void TC07(){
        int postid =1;
        response=listNestedResources(postid);

        Assert.assertEquals(response.getStatusCode(),200);

        List<Integer> idlist= response.jsonPath().get("postId");
        for(int i:idlist){
            Assert.assertEquals(i,postid);
        }
    }

}
