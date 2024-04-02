package edu.ucr.cs.cs167.cchua032;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        // Check if exactly two arguments are provided
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments! Expected two arguments.");
            System.exit(-1);
        }

        // Store the command line arguments in Path variables
        Path input = new Path(args[0]);
        Path output = new Path(args[1]);

        // Retrieve the file system
        FileSystem fs = input.getFileSystem(new Configuration());

        // Check if input file exists
        if (!fs.exists(input)) {
            System.err.printf("Input file '%s' does not exist!\n", input);
            System.exit(-1);
        }

        // Check if output file already exists
        if (fs.exists(output)) {
            System.err.printf("Output file '%s' already exists!\n", output);
            System.exit(-1);
        }

        // Copying the file
        long startTime = System.nanoTime();
        fs.copyToLocalFile(input, output);
        long endTime = System.nanoTime();
        long bytesCopied = fs.getFileStatus(output).getLen();
        // Printing the success message
        System.out.printf("Copied %d bytes from '%s' to '%s' in %f seconds\n",
                bytesCopied, input, output, (endTime - startTime) * 1E-9);
    }
}
