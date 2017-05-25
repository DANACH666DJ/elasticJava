package elasticsearch;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.node.Node;

import com.opencsv.CSVReader;

public class elastic {
	private String cluster;
	private Node node;
	private TransportClient client;
	private String indexName;
	private Settings settings;
	private Map<String, Object> jsonDocument;
	private String csvFile;
	private CSVReader reader;

	public elastic() {
		cluster = "elasticsearch";
		indexName = "";
		// node =
		// NodeBuilder.nodeBuilder().client(false).local(false).settings(Settings.settingsBuilder()
		// .put("path.home", "C:\\elasticsearch-2.4.5").put("cluster.name",
		// cluster)).node();
		// client = node.client();
		settings = Settings.settingsBuilder().put("cluster.name", cluster).build();
		try {
			client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, Object> putJsonDocument(String forumUrl, String userAname, int postNumA, String userAurl,
			String userQTxt, int postNumPatGrate, String usrAspecial, String uniqIdMedc, String userATxt,
			String userAnumCollege, int postNumQ, int postNumExpAgre, String forumTitle, String userAcity) {

		jsonDocument = new HashMap<String, Object>();
		jsonDocument.put("forumUrl", forumUrl);
		jsonDocument.put("userAname", userAname);
		jsonDocument.put("postNumA", postNumA);
		jsonDocument.put("userAurlr", userAurl);
		jsonDocument.put("userQTxt", userQTxt);
		jsonDocument.put("postNumPatGrate", postNumPatGrate);
		jsonDocument.put("usrAspecial", usrAspecial);
		jsonDocument.put("uniqIdMedc", uniqIdMedc);
		jsonDocument.put("userATxt", userATxt);
		jsonDocument.put("userAnumCollege", userAnumCollege);
		jsonDocument.put("postNumQ", postNumQ);
		jsonDocument.put("postNumExpAgre", postNumExpAgre);
		jsonDocument.put("forumTitle", forumTitle);
		jsonDocument.put("userAcity", userAcity);

		return jsonDocument;
	}

	public void createIndex(String indexName) {
		this.indexName = indexName;
		Thread hilo;
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// search if index exist in elasticSearch
		try {
			client.admin().indices().prepareCreate(indexName).get();
			System.out.println("Creating index in elasticSearch...");
		} catch (IndexAlreadyExistsException e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
			// if index exist close the program
			boolean exists = client.admin().indices().prepareExists(indexName).execute().actionGet().isExists();
			if (exists) {
				System.out.println("The Index was create");
			}
			System.exit(0);
		}

	}

	public void postElasticSearch(String docType) {
		csvFile = "/Users/Daniel.Garcimartin/PycharmProjects/foroDoctoralia/z.csv";
		reader = null;
		try {
			// reader = new CSVReader(new FileReader(csvFile));
			reader = new CSVReader(new InputStreamReader(new FileInputStream(csvFile), "UTF-8"));
			String[] documents;
			String[] noLoQuiero = reader.readNext();
			while ((documents = reader.readNext()) != null) {
				String forumUrl = (String) documents[0];
				String userAname = (String) documents[1];
				int postNumA = Integer.parseInt(documents[2]);
				String userAurlr = (String) documents[3];
				String userQTxt = (String) documents[4];
				int postNumPatGrate = Integer.parseInt(documents[5]);
				String usrAspecial = (String) documents[6];
				String uniqIdMedc = (String) documents[7];
				String userATxt = (String) documents[8];
				String userAnumCollege = (String) documents[9];
				int postNumQ = Integer.parseInt(documents[10]);
				int postNumExpAgre = Integer.parseInt(documents[11]);
				String forumTitle = (String) documents[12];
				String userAcity = (String) documents[13];

				System.out.println("forumUrl***" + forumUrl + "name***" + userAname + "POSTnumAnswers***" + postNumA
						+ "user_answer_url***" + userAurlr + "user_question_text***" + userQTxt
						+ "post_num_patients_grateful***" + postNumPatGrate + "user_answer_specialities***"
						+ usrAspecial + "unique_id_medicament***" + uniqIdMedc + "user_answer_text***" + userATxt
						+ "user_answer_num_college***" + userAnumCollege + "post_num_questions***" + postNumQ
						+ "post_num_experts_agreement***" + postNumExpAgre + "forum_title***" + forumTitle
						+ "user_answer_city***" + userAcity);

				client.prepareIndex(indexName, docType)
						.setSource(putJsonDocument(forumUrl, userAname, postNumA, userAurlr, userQTxt, postNumPatGrate,
								usrAspecial, uniqIdMedc, userATxt, userAnumCollege, postNumQ, postNumExpAgre,
								forumTitle, userAcity))
						.get();
			

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeClient() {
		client.close();
	}
}
