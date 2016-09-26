# RedditNGramTokenizer

This java program read each line of the Reddit generated comment files (from this project ...) and tokenize them into 1-5 grams. There are two version: serial and multi-threaded computing. As the multi-threaded version needs at least 6-8 TB RAM to process each file so I run the program in serial mode in default (which requires 2TB RAM). An example to process a monthly file in Hungabee (https://www.westgrid.ca/support/quickstart/hungabee) and requests 2TB RAM.

#!/bin/bash
#PBS -S /bin/bash
#PBS -l procs=256
#PBS -M dtuananh@gmail.com
#PBS -m bea
#PBS -o /home/hellovn/ngrams/out2015_9.out
#PBS -e /home/hellovn/ngrams/err2015_9.err

/home/hellovn/ngrams/jdk1.8.0_73/bin/java -jar -Xmx2000g -Xms2000g -jar /home/h$


