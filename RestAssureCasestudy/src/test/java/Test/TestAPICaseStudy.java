package Test;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import Project.Assertion;
import Project.BaseTestCaseStudy;
import Project.RestCallsCaseStudy;
import Utility.Testutil;
import Utility.URLCaseStudy;
import Utility.payloadconvertorcasestudy;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestAPICaseStudy {
	
	private String accessToken;
	static Response response;
	String addproductid=null;
	
	@BeforeTest
	public void setup() throws IOException
	{
		accessToken = BaseTestCaseStudy.Signup();
		
	}	
	
	@Test(priority=0)
	public void login() throws IOException
	{
		//this line will go to resource and fetch thepayload and convert it to string
		String loginpayload = payloadconvertorcasestudy.generatepayloadstring("login.json");
		
		//this line will fetch the URL along with resource
		String endpointURI = URLCaseStudy.getEndpoint("/login");
		
		//I have to call the method which contains the post method from the restcall
	response = RestCallsCaseStudy.PostRequestauthorization(endpointURI,loginpayload,accessToken);
	String strresponse = Testutil.getResponsestring(response);
	JsonPath jsonres = Testutil.jsonparser(strresponse);
	Assertion.verifystatuscode(response, 200);
	}
	
	@Test(priority=1)
	public void GetUser()
	{
		String endpointURL = URLCaseStudy.getEndpoint("/user");	
		response = RestCallsCaseStudy.GetRequestHeader(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String successuser = jsonres.getString("data.username");
		Assertion.verifystatuscode(response, 200);
		
		
	}
	

	@Test(priority=2)
	public void GetCategories()
	{
		
		String endpointURL = URLCaseStudy.getEndpoint("/categories");	
		response = RestCallsCaseStudy.GetRequestHeader(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String successcategories = jsonres.getString("success");
		Assertion.verifystatuscode(response, 200);
		
		
	}
	
	@Test(priority=3)
	public void AddProductCategories() throws IOException
	{
		String loginpayload = payloadconvertorcasestudy.generatepayloadstring("addproductcategory.json");
		String endpointURL = URLCaseStudy.getEndpoint("/categories");	
		response = RestCallsCaseStudy.PostRequestauthorization(endpointURL,loginpayload,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		addproductid = jsonres.getString("data.id");
		Assertion.verifystatuscode(response, 200);
		
		
		
		
	}
	@Test(priority=4)
	public  void GetProductCategoriesByID() throws IOException
	{
		
		String endpointURL = URLCaseStudy.getEndpoint("/categories/"+addproductid);	
		response = RestCallsCaseStudy.GetRequestHeader(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String getcatogeryid = jsonres.getString("data.category_id");
		Assertion.verifystatuscode(response, 200);
		
		
	}
	@Test(priority=5)
	public  void DeleteProductCategoriesByID() throws IOException
	{
		
		System.out.println(addproductid);
		String endpointURL = URLCaseStudy.getEndpoint("/categories/"+addproductid);	
		response = RestCallsCaseStudy.DeleteRequest(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String deletecatogeryid = jsonres.getString("success");
		Assertion.verifystatuscode(response, 200);
		
		
	}
	
	@Test(priority=6)
	public  void VerifyDeleteProductCategoriesByID() throws IOException
	{
		
		
		String endpointURL = URLCaseStudy.getEndpoint("/categories/"+addproductid);	
		response = RestCallsCaseStudy.GetRequestHeader(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String deletecatogeryid = jsonres.getString("error");
		Assertion.verifystatuscode(response, 404);
		
		
	}
	
	
	@Test(priority=7)
	public void Getlogout()
	{
		
		String endpointURL = URLCaseStudy.getEndpoint("/logout");	
		response = RestCallsCaseStudy.PostRequest(endpointURL,accessToken);
		String strresponse = Testutil.getResponsestring(response);
		JsonPath jsonres = Testutil.jsonparser(strresponse);
		String successuser = jsonres.getString("success");
		Assertion.verifystatuscode(response, 200);
		
		
	}

	
}
