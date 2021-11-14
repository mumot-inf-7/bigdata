import com.google.gson.Gson;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import com.opencsv.CSVParser;

import java.io.IOException;

public class AvgSizeStations extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new AvgSizeStations(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), "AvgSizeStations");
        job.setJar("avgsizestations.jar");
        job.setJar("opencsv.jar");
        job.setJarByClass(this.getClass());
        job.setJarByClass(CSVParser.class);
        job.setJarByClass(AvgSizeStationMapper.class);
        job.setJarByClass(AvgSizeStations.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(AvgSizeStationMapper.class);
        job.setReducerClass(AvgSizeStationReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class AvgSizeStationMapper extends Mapper<LongWritable, Text, Text, Text> {

        private final Text keoOut = new Text();
        private final Text valueOut = new Text();

        public void map(LongWritable offset, Text lineText, Context context) {
            Gson gson = new Gson();

            try {
                if (offset.get() != 0) {
                    String line = lineText.toString();
                    LineData lineData = new LineReader().parse(line);
                    if(lineData.getYear() <= 2012){
                        return;
                    }
                    if(lineData.getCode().equals(""))
                        return;

                    lineData.getStreets().forEach(s -> {
                        keoOut.set(lineData.getCode()+"-"+s);
//                        valueOut.set(lineData.getAllInjured()+ lineData.getAllKilled());
                        valueOut.set(gson.toJson(lineData));
                        try {
                            context.write(keoOut, valueOut);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class AvgSizeStationReducer extends Reducer<Text, Text, Text, Text> {


        @Override
        public void reduce(Text key, Iterable<Text> values,
                           Context context) throws IOException, InterruptedException {
            Gson gson = new Gson();
            Text resultKey = new Text(key);
            KilledData result = KilledData.getData();

            values.forEach(text -> {
                result.add(gson.fromJson(text.toString(), KilledData.class));
            });

            Text resultVal = new Text(gson.toJson(result));

            context.write(resultKey, resultVal);
        }
    }

    public static class AvgSizeStationCombiner extends Reducer<Text, SumCount, Text, SumCount> {

        private final SumCount sum = new SumCount(0.0d, 0);

        @Override
        public void reduce(Text key, Iterable<SumCount> values, Context context) throws IOException, InterruptedException {

            sum.set(new DoubleWritable(0.0d), new IntWritable(0));

            for (SumCount val : values) {
                sum.addSumCount(val);
            }
            context.write(key, sum);
        }
    }
}