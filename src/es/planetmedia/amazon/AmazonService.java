/*
* Clase que se comunica con los servicios Amazon s3
* @author Josete
* @date 20-05-2016
*/

package es.planetmedia.amazon;

import java.io.File;
import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import es.planetmedia.jvm.JvmEnviroment;


public class AmazonService {

	/**
	 * Sube un fichero a Amazon S3
	 * @param file fichero que se va subir
	 * @param key identificador del fichero en el bucket de amazon 
	 **/
	public void uploadFileToAmazonS3(File file, String key)
	{
		
        AWSCredentials credentials = null;
        String bucketName = JvmEnviroment.getInstance().getBucketNameJVM();
        try {
        	String accessKey = JvmEnviroment.getInstance().getBucketAccessKeyJVM();
        	String secretKey = JvmEnviroment.getInstance().getBucketSecretKeyJVM();
            credentials = new BasicAWSCredentials( accessKey, secretKey);
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location, and is in valid format.",
                    e);
        }
        
        AmazonS3 s3 = new AmazonS3Client(credentials);
        
        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");

        try {
             /*
              * List the buckets in your account
              */
             boolean encontrado = false;
             Bucket bucketScanlife = new Bucket();
             Iterator<Bucket> buck = s3.listBuckets().iterator();
             while (buck.hasNext() && !encontrado){
             	bucketScanlife = buck.next();
             	if (bucketScanlife.getName().equals(bucketName)) {
             		System.out.println("Recuperado el bucket " + bucketName);
             		encontrado = true;
             	}
             }
             
             /*
              * Upload an object to your bucket - You can easily upload a file to
              * S3, or upload directly an InputStream if you know the length of
              * the data in the stream. You can also specify your own metadata
              * when uploading to S3, which allows you set a variety of options
              * like content-type and content-encoding, plus additional metadata
              * specific to your applications.
              */
             
             System.out.println("Uploading a new object to S3 from a file\n");
             
             s3.putObject(new PutObjectRequest(bucketName, key, file));
            
         } 
          catch (AmazonServiceException ase) {
             System.out.println("Caught an AmazonServiceException, which means your request made it "
                     + "to Amazon S3, but was rejected with an error response for some reason.");
             System.out.println("Error Message:    " + ase.getMessage());
             System.out.println("HTTP Status Code: " + ase.getStatusCode());
             System.out.println("AWS Error Code:   " + ase.getErrorCode());
             System.out.println("Error Type:       " + ase.getErrorType());
             System.out.println("Request ID:       " + ase.getRequestId());
             
          } catch (AmazonClientException ace) {
             System.out.println("Caught an AmazonClientException, which means the client encountered "
                     + "a serious internal problem while trying to communicate with S3, "
                     + "such as not being able to access the network.");
             System.out.println("Error Message: " + ace.getMessage());
            
         }
		
	}
	
	/**
	 * Obtiehe un fichero de Amazon S3
	 * @param key identificador del fichero en el bucket de amazon 
	 * @return S3Object fichero solicitado
	 */
	public S3Object getFileS3(String key)
	{
		AWSCredentials credentials = null;
		String bucketName = JvmEnviroment.getInstance().getBucketNameJVM();
        try {
        	String accessKey = JvmEnviroment.getInstance().getBucketAccessKeyJVM();
        	String secretKey = JvmEnviroment.getInstance().getBucketSecretKeyJVM();
            credentials = new BasicAWSCredentials(accessKey, secretKey);
            //  credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location, and is in valid format.",
                    e);
        }
		
        AmazonS3 s3 = new AmazonS3Client(credentials);
        
//        System.out.println("===========================================");
//        System.out.println("getFileS3 with Amazon S3");
//        System.out.println("===========================================\n");

        try {
             /*
              * List the buckets in your account
              */
             boolean encontrado = false;
             Bucket bucketScanlife = new Bucket();
             Iterator<Bucket> buck = s3.listBuckets().iterator();
             while (buck.hasNext() && !encontrado){
             	bucketScanlife = buck.next();
             	if (bucketScanlife.getName().equals(bucketName)) {
//             		System.out.println("Recuperado el bucket " + bucketName);
             		encontrado = true;
             	}
             }
             /*
              * Download an object - When you download an object, you get all of
              * the object's metadata and a stream from which to read the contents.
              * It's important to read the contents of the stream as quickly as
              * possibly since the data is streamed directly from Amazon S3 and your
              * network connection will remain open until you read all the data or
              * close the input stream.
              *
              * GetObjectRequest also supports several other options, including
              * conditional downloading of objects based on modification times,
              * ETags, and selectively downloading a range of an object.
              */
             System.out.println("Downloading an object");
             S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
             
             return object;
             
         } 
          catch (AmazonServiceException ase) {
             System.out.println("Caught an AmazonServiceException, which means your request made it "
                     + "to Amazon S3, but was rejected with an error response for some reason.");
             System.out.println("Error Message:    " + ase.getMessage());
             System.out.println("HTTP Status Code: " + ase.getStatusCode());
             System.out.println("AWS Error Code:   " + ase.getErrorCode());
             System.out.println("Error Type:       " + ase.getErrorType());
             System.out.println("Request ID:       " + ase.getRequestId());
             return null;
          } catch (AmazonClientException ace) {
             System.out.println("Caught an AmazonClientException, which means the client encountered "
                     + "a serious internal problem while trying to communicate with S3, "
                     + "such as not being able to access the network.");
             System.out.println("Error Message: " + ace.getMessage());
             return null;
         }
		
	}
}
