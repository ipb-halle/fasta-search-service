## Result files from FASTA runs for testing the FastaResultParser class ##

### Protein queries to protein databases (fasta) ###

Some example sequence data was taken from [renzok/docker-ncbi-blast-demo](https://github.com/renzok/docker-ncbi-blast-demo/blob/master/bet_blaSHV.fasta).

#### (1) results1.txt ####
* normal fasta36 run
* `$FASTA_PATH/bin/fasta36 -m 10 query1.fasta data1.fasta > results1.txt`

#### (2) results2.txt ####
* missing description fields for the query and subject sequences
* `$FASTA_PATH/bin/fasta36 -m 10 query2.fasta data2.fasta > results2.txt`

#### (3) results3.txt ####
* like (2), but with interchanged the query and subject sequences
* `$FASTA_PATH/bin/fasta36 -m 10 query3.fasta data3.fasta > results3.txt`

#### (4) results4.txt ####
* like (2), but with missing name and description in query and subject sequence, i.e. only ">" in the header line
* `$FASTA_PATH/bin/fasta36 -m 10 query4.fasta data4.fasta > results4.txt`

#### (5) results5.txt ####
* empty header line in the subject sequence
* gives empty result set
* `$FASTA_PATH/bin/fasta36 -m 10 query5.fasta data5.fasta > results5.txt`

#### (6) results6.txt ####
* empty header line in the query sequence
* fasta36 logs an error
* `$FASTA_PATH/bin/fasta36 -m 10 query6.fasta data6.fasta > results6.txt`

#### (7) results7.txt ####
* normal fasta36 run, but with different library (7 sequences)
* result set contains 9 results, i.e. two library sequences have two hits
* `$FASTA_PATH/bin/fasta36 -m 10 query7.fasta data7.fasta > results7.txt`

### DNA queries to DNA databases (fasta) ###

#### (8) results8.txt ####
* normal fasta36 run
* `$FASTA_PATH/bin/fasta36 -m 10 -n query8.fasta data8.fasta > results8.txt`

### DNA queries to protein databases (fastx/fasty) ###

#### (9) results9.txt ####
* normal fastx36 run
* `$FASTA_PATH/bin/fastx36 -m 10 -n query9.fasta data9.fasta > results9.txt`

#### (10) results10.txt ####
* normal fasty36 run
* `$FASTA_PATH/bin/fasty36 -m 10 -n query10.fasta data10.fasta > results10.txt`

### Protein queries to DNA databases (tfastx/tfasty) ###

#### (11) results11.txt ####
* normal tfastx36 run
* `$FASTA_PATH/bin/tfastx36 -m 10 query11.fasta data11.fasta > results11.txt`

#### (12) results12.txt ####
* normal tfasty36 run
* `$FASTA_PATH/bin/tfasty36 -m 10 query12.fasta data12.fasta > results12.txt`

### Frame shifts ###
This example tries to reproduce Fig. 5 in *W. R. Pearson, T. C. Wood, Z. Zhang, and W. Miller*: Comparison of DNA sequences with protein sequences. Genomics, 46:24-36, 1997 ([doi:10.1006/geno.1997.4995](https://doi.org/10.1006/geno.1997.4995)). The underlying sequences were taken from the [fasta36 repository](https://github.com/wrpearson/fasta36/tree/v36.3.8/seq).

#### results_fastx_mgstm1.txt ####
* `$FASTA_PATH/bin/fastx36 -m 10 -n mgstm1.e05 mgstm1.aa > results_fastx_mgstm1.txt`

### Just for fun (and testing) ###

#### results_tfastx_mgstm1_1.txt ####
* `$FASTA_PATH/bin/tfastx36 -m 10 -p mgstm1.e05 mgstm1.aa > results_tfastx_mgstm1_1.txt`
* infinite number for *tfx_bits*
* reproducibility: not every tfastx36 run shows such a result

#### results_tfastx_mgstm1_2.txt ####
* `$FASTA_PATH/bin/tfastx36 -m 10 -p mgstm1.aa mgstm1.e05 > results_tfastx_mgstm1_2.txt`
* the name of the query sequence is very long, thus fasta truncates it
* query sequence has a '-' character