package Init;

import java.io.IOException;

import Import.UpdateCSV;

public class Index {

	public static void main(String[] args) throws IOException {
		
		UpdateCSV csv = new UpdateCSV();
		csv.appendAtFirst("Vision", "XXX");
		csv.filterReport("Vision");
		
		csv.appendAtFirst("VSM", "XXX");
		csv.filterReport("VSM");
	}
}
