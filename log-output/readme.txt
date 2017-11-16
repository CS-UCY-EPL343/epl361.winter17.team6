//Use those two lines before testing to output text in this logfile.

PrintStream out = new PrintStream(new FileOutputStream("log-output/output.txt"));
System.setOut(out);