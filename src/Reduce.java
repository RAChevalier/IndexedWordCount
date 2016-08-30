import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.HashMap;

public class Reduce extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		// store the number of words per file in a hashmap
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for (Text val : values) {
			if (result.containsKey(val.toString())) {
				result.put(val.toString(), result.get(val.toString()) + 1);
			} else {
				result.put(val.toString(), 1);
			}
		}

		// form output string to display reduce result
		String output = "{";
		int sum = 0;
		int size = result.size();
		for (String data : result.keySet()) {
			int n = result.get(data);
			sum += n;
			if (size == 1) {
				output += data + "=" + n;
			} else {
				output += data + "=" + n + ", ";
				size--;
			}
		}

		output += "}\n Total occurrence of \"" + key.toString() + "\": " + sum;
		// write output
		context.write(key, new Text(output));
	}
}