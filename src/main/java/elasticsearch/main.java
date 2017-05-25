package elasticsearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class main {
	public static void main(String[] args) {
		elastic es = new elastic();
		// create index with name
		es.createIndex("medicaments_z");
		// create type index and source json
		es.postElasticSearch("Z");
		// close the client
		es.closeClient();

	}

}
