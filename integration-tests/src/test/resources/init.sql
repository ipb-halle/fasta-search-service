CREATE TABLE sequences (
	id INTEGER PRIMARY KEY,
	sequencetype TEXT NOT NULL,
	description TEXT,
	sequence TEXT NOT NULL
);

-- data from fasta-search-service/service/src/test/resources/de/ipb_halle/fasta_search_service/fastaresult/data7.fasta or data9.fasta
INSERT INTO sequences VALUES (1, 'PROTEIN', 'sp|Q9K9L8|GLSA1_BACHD Glutaminase 1 OS=Bacillus halodurans (strain ATCC BAA-125 / DSM 18197 / FERM 7344 / JCM 9153 / C-125) OX=272558 GN=glsA1 PE=3 SV=1', 'MWKQDETLEQIVLECKKYTEEGTVASYIPALAKADVSTLGIAIYRGGDEQVIAGDADEKFTLSISKVIALALALLDVGEEAVFSKVGMEPTGDPFNSISKLETSVPSKPLNPMINAGALAVTNMIIGETTEQSLGRLLSFIHELTKNPTITYNLEVAQSEFDTAFLNRSLSYFLKQHGVIQADVEQLLDLYTKQCAIEMCCSDLARIGYVFANEGRDPDTGQRIVPLHVARIIKTFMVTCGMYNASGEFAIRVGIPAKSGVSGAILALVPNKYGIAVYSPALDEKGNSLAGIKLLETLSCREEWSIF');
INSERT INTO sequences VALUES (2, 'PROTEIN', 'sp|O65976|BLC6_SALTM Beta-lactamase CTX-M-6 OS=Salmonella typhimurium OX=90371 GN=bla PE=3 SV=1', 'MMTQSIRRSMLTVMATLPLLFSSATLHAQANSVQQQLEALEKSSGGRLGVALINTADNSQILYVADERFAMCSTSKVMAAAAVLKQSESDKHLLNQRVEIRASDLVNYNPIAEKHVNGTMTLAQLGAGALQYSDNTAMNKLIAHLGGPDKVTAFARSLGDETFRLDRTEPTLNSAIPGDPRDTTTPLAMAQTLKNLTLGKALAETQRAQLVTWLKGNTTGSASIRAGLPKSWGVGDKTGSGDYGTTNDIAVIWPENHAPLVLVTYFTQPEQKAESRRDVLAAAAKIVTHGF');
INSERT INTO sequences VALUES (3, 'PROTEIN', 'sp|O69395|BLT2_ECOLX Beta-lactamase Toho-2 OS=Escherichia coli OX=562 GN=bla PE=3 SV=1', 'MVTKRVQRMMSAAAACIPLLLGSPTLYAQTSAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAAAAVLKQSETQKQLLNQPVEIKPADLVNYNPIAEKHVNGTMTLAELSAAALQYSDNTAMNKLIAQLGGPGGVTAFARAIGDETFRLDRTEPTLNTAIPGDPRDTTTARAGADVASLRWVMRWAKPSGAVGDVAQRQYDRAAGIRAGLPTSWTVGDKTGSGDYGTTNDIAVIWPQGRAPLVLVTYFTQPQQNAESRRDVLASAARIIAEGL');
INSERT INTO sequences VALUES (4, 'PROTEIN', 'sp|Q8CDJ3|BAKOR_MOUSE Beclin 1-associated autophagy-related key regulator OS=Mus musculus OX=10090 GN=Atg14 PE=1 SV=1', 'MASPSGKGSWTPEAPGFGPRALARDLVDSVDDAEGLYVAVERCPLCNTTRRRLTCAKCVQSGDFVYFDGRDRERFIDKKERLSQLKNKQEEFQKEVLKAMEGKRLTDQLRWKIMSCKMRIEQLKQTICKGNEEMKKNSEGLLKNKEKNQKLYSRAQRHQEKKEKIQRHNRKLGDLVEKKTIDLKSHYERLARLRRSHILELTSIIFPIDEVKTSGRDPADVSSETDSAMTSSMVSKLAEARRTTYLSGRWVCDDHNGDTSISITGPWISLPNNGDYSAYYNWVEEKKTTQGPDMEHNNPAYTISAALGYATQLVNIVSHILDINLPKKLCNSEFCGENLSKQKLTRAVRKLNANILYLCSSQHVNLDQLQPLHTLRNLMHLVSPRSEHLGRSGPFEVRADLEESMEFVDPGVAGESDASGDERVSDEETDLGTDWENLPSPRFCDIPSQPVEVSQSQSTQVSPPIASSSAGGMISSAAASVTSWFKAYTGHR');
INSERT INTO sequences VALUES (5, 'PROTEIN', 'sp|Q03680|BLA1_STRCI Beta-lactamase 1 OS=Streptomyces cacaoi OX=1898 GN=blaL PE=1 SV=1', 'MRIRPTRRLLLGAVAPLALVPLVACGQASGSESGQQPGLGGCGTSAHGSADAHEKEFRALEKKFDAHPGVYAIDTRDGQEITHRADERFAYGSTFKALQAGAILAQVLRDGREVRRGAEADGMDKVVHYGQDAILPNSPVTEKHVADGMSLRELCDAVVAYSDNTAANLLFDQLGGRRGSTRVLKQLGDHTTSMDRYEQELGSAVPGDPRDTSTPRAFAEDLRAFAVEDGEKAALAPNDREQLNDWMSGSRTGDALIRAGVPKDWKVEDKSGQVKYGTRNDIAVVRPPGRAPIVVSVMSHGDTQDAEPHDELVAEAGLVVADGLK');
INSERT INTO sequences VALUES (6, 'PROTEIN', 'sp|P00809|BLAC_BACCE Beta-lactamase 1 OS=Bacillus cereus OX=1396 GN=blaY PE=3 SV=1', 'MILKNKRMLKIGICVGILGLSITSLEAFTGESLQVEAKEKTGQVKHKNQATHKEFSQLEKKFDARLGVYAIDTGTNQTISYRPNERFAFASTYKALAAGVLLQQNSIDSLNEVITYTKEDLVDYSPVTEKHVDTGMKLGEIAEAAVRSSDNTAGNILFNKIGGPKGYEKALRHMGDRITMSNRFETELNEAIPGDIRDTSTAKAIATNLKAFTVGNALPAEKRKILTEWMKGNATGDKLIRAGIPTDWVVGDKSGAGSYGTRNDIAVVWPPNSAPIIVLISSKDEKEAIYNDQLIAEATKVIVKGS');
INSERT INTO sequences VALUES (7, 'PROTEIN', 'sp|P00807|BLAC_STAAU Beta-lactamase OS=Staphylococcus aureus OX=1280 GN=blaZ PE=1 SV=1', 'MKKLIFLIVIALVLSACNSNSSHAKELNDLEKKYNAHIGVYALDTKSGKEVKFNSDKRFAYASTSKAINSAILLEQVPYNKLNKKVHINKDDIVAYSPILEKYVGKDITLKALIEASMTYSDNTANNKIIKEIGGIKKVKQRLKELGDKVTNPVRYEIELNYYSPKSKKDTSTPAAFGKTLNKLIANGKLSKENKKFLLDLMLNNKSGDTLIKDGVPKDYKVADKSGQAITYASRNDVAFVYPKGQSEPIVLVIFTNKDNKSDKPNDKLISETAKSVMKEF');

-- data from fasta-search-service/service/src/test/resources/de/ipb_halle/fasta_search_service/fastaresult/data8.fasta or data11.fasta
INSERT INTO sequences VALUES (8, 'DNA', 'ENA|BAB06346|BAB06346.1 Bacillus halodurans C-125 glutaminase', 'ATGTGGAAGCAGGATGAGACGCTGGAGCAAATTGTATTAGAATGCAAAAAATATACGGAAGAGGGTACCGTTGCTTCTTATATTCCAGCGCTAGCAAAAGCTGATGTCTCAACATTAGGCATTGCGATTTATCGCGGTGGAGACGAGCAAGTAATAGCTGGAGATGCGGACGAGAAATTTACTTTGCAAAGCATATCGAAGGTCATTGCCTTAGCGTTAGCTTTATTGGATGTAGGAGAAGAAGCGGTATTTTCCAAAGTAGGGATGGAACCAACAGGGGACCCTTTTAATTCGATTTCAAAGCTGGAAACGAGCGTCCCCTCAAAACCGTTAAACCCGATGATCAACGCGGGTGCGTTAGCCGTTACAAATATGATCATCGGTGAAACGACGGAGCAATCGCTAGGCAGGCTGCTCTCGTTTATTCACGAACTAACCAAAAATCCAACGATTACGTACAACCTTGAAGTGGCTCAATCTGAATTTGATACGGCTTTTTTAAACCGTTCATTAAGCTATTTTTTGAAACAGCATGGTGTGATTCAAGCGGATGTGGAACAGCTTCTGGATTTATACACGAAGCAGTGTGCGATTGAAATGTGTTGCTCCGATTTGGCGCGCATAGGCTATGTATTTGCCAATGAAGGGAGAGACCCTGATACTGGGCAGCGGATTGTTCCTCTTCATGTCGCCCGAATCATTAAAACGTTCATGGTCACATGTGGAATGTACAATGCCTCAGGCGAATTCGCCATCCGTGTGGGCATTCCAGCAAAAAGCGGAGTATCTGGTGCAATACTTGCATTGGTCCCGAATAAGTATGGAATTGCCGTTTACAGTCCTGCACTAGACGAGAAAGGAAATAGTCTAGCTGGAATTAAATTACTAGAAACGCTCTCTTGCCGTGAAGAATGGAGTATTTTTTGA');
INSERT INTO sequences VALUES (9, 'DNA', 'ENA|CAA06312|CAA06312.1 Salmonella enterica subsp. enterica serovar Typhimurium CTX-M-7', 'ATGATGACTCAGAGCATTCGCCGCTCAATGTTAACGGTGATGGCGACGCTACCCCTGCTATTTAGCAGCGCAACGCTGCATGCGCAGGCGAACAGCGTGCAACAGCAGCTGGAAGCCCTGGAGAAAAGTTCGGGAGGTCGGCTTGGCGTTGCGCTGATTAACACCGCCGATAATTCGCAGATTCTCTACGTGGCCGATGAGCGTTTTGCGATGTGCAGTACCAGTAAGGTGATGGCGGCCGCGGCGGTGCTTAAACAGAGCGAGAGCGATAAGCACCTGCTAAATCAGCGCGTTGAAATCAGAGCAAGCGACCTGGTTAACTACAATCCGATTGCGGAGAAACACGTTAACGGCACGATGACGCTGGCTCAGCTTGGCGCCGGCGCCCTGCAGTATAGCGACAATACTGCCATGAATAAGCTGATTGCCCATCTGGGTGGGCCCGATAAAGTGACGGCGTTTGCTCGCTCATTGGGTGATGAGACCTTCCGTCTGGACAGAACCGAGCCCACGCTCAATAGCGCCATTCCAGGCGACCCGCGTGATACCACCACGCCGCTCGCGATGGCGCAGACCCTGAAAAATCTGACGCTGGGCAAAGCGCTGGCGGAAACTCAGCGGGCACAGTTGGTGACGTGGCTTAAGGGCAATACTACCGGTAGCGCGAGCATTCGGGCGGGTCTGCCGAAATCATGGGGAGTGGGCGATAAAACCGGCAGCGGAGATTATGGCACCACCAACGATATCGCGGTTATCTGGCCGGAAAACCACGCACCGCTGGTTCTGGTGACCTACTTTACCCAACCGGAGCAGAAGGCGGAAAGCCGTCGGGATGTTCTGGCTGCGGCGGCGAAAATCGTAACCCACGGTTTCTGA');
INSERT INTO sequences VALUES (10, 'DNA', 'ENA|BAA28282|BAA28282.1 Escherichia coli beta-lactamase', 'ATGGTGACAAAGAGAGTGCAACGGATGATGTCCGCGGCGGCGGCGTGCATTCCGCTGCTGCTGGGCAGCCCAACGCTTTATGCGCAGACGAGTGCGGTGCAGCAAAAGCTGGCGGCGCTGGAGAAAAGCAGCGGAGGGCGGCTGGGCGTCGCGCTCATCGATACCGCAGATAATACGCAGGTGCTTTATCGCGGTGATGAACGCTTTCCAATGTGCAGTACCAGTAAAGTTATGGCGGCCGCGGCGGTGCTTAAGCAGAGTGAAACGCAAAAGCAGCTGCTTAATCAGCCTGTCGAGATCAAGCCTGCCGATCTGGTTAACTACAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAACTGAGCGCGGCCGCTTTGCAGTACAGCGACAATACCGCCATGAACAAATTGATTGCCCAGCTCGGTGGCCCGGGAGGCGTGACGGCTTTTGCCCGCGCGATCGGCGATGAGACGTTTCGTCTGGATCGCACTGAACCTACGCTGAATACCGCCATTCCCGGCGACCCGAGAGACACCACCACGGCGCGGGCTGGCGCAGACGTTGCGTCATTACGCTGGGTCATGCGCTGGGCGAAACCCAGCGGCGCAGTTGGTGACGTGGCTCAAAGGCAATACGACCGCGCAGCCGGCATTCGGGCCGGCTTACCGACGTCGTGGACTGTGGGTGATAAGACCGGCAGCGGCGACTACGGCACCACCAATGATATTGCGGTGATCTGGCCGCAGGGTCGTGCGCCGCTGGTTCTGGTGACCTATTTTACCCAGCCGCAACAGAACGCAGAGAGCCGCCGCGATGTGCTGGCTTCAGCGGCGAGAATCATCGCCGAAGGGCTGTAA');
INSERT INTO sequences VALUES (11, 'DNA', 'ENA|BAC26705|BAC26705.1 Mus musculus (house mouse) hypothetical protein', 'ATGGCGTCTCCCAGTGGGAAGGGATCTTGGACGCCCGAGGCTCCTGGTTTTGGGCCGCGGGCGCTAGCACGGGACCTGGTGGACTCGGTGGACGACGCCGAGGGCCTTTACGTGGCTGTTGAGCGGTGTCCTCTGTGCAACACCACTCGCCGGCGGTTGACTTGCGCCAAGTGCGTCCAGAGCGGTGATTTCGTCTATTTCGACGGCCGCGACCGGGAGAGGTTTATTGACAAGAAGGAAAGACTAAGCCAACTTAAGAACAAGCAAGAAGAATTTCAGAAAGAAGTACTAAAAGCTATGGAAGGAAAGCGGCTTACAGATCAGTTGAGATGGAAAATAATGTCATGCAAGATGAGGATTGAACAGCTGAAGCAAACAATATGTAAAGGAAATGAGGAAATGAAGAAAAATTCTGAAGGTCTCCTCAAGAACAAGGAAAAGAACCAGAAGCTTTACAGCCGAGCACAGCGGCACCAAGAGAAAAAGGAGAAGATTCAGCGGCACAACCGCAAGCTTGGGGACCTGGTGGAGAAGAAGACCATTGACTTGAAGAGTCACTATGAGCGGTTGGCGCGGCTTCGAAGGTCACACATCCTAGAGCTCACCTCCATCATATTCCCAATCGACGAAGTGAAGACTTCTGGGAGAGACCCTGCAGACGTGTCTTCAGAGACTGACAGTGCCATGACCTCAAGCATGGTGAGCAAGCTTGCTGAGGCCCGGAGGACAACTTACCTCTCTGGAAGATGGGTCTGTGATGACCACAATGGTGACACCAGCATTAGCATCACAGGCCCGTGGATTAGCCTACCAAACAACGGGGACTACTCTGCTTACTACAATTGGGTAGAAGAGAAGAAAACAACCCAAGGACCTGACATGGAGCATAACAACCCCGCCTACACTATCAGCGCCGCGCTGGGCTACGCCACGCAGCTCGTCAACATTGTGTCTCACATACTTGACATCAATCTTCCCAAAAAGCTGTGCAACAGCGAGTTCTGTGGCGAAAACCTCAGCAAGCAGAAACTGACACGCGCAGTGAGGAAACTGAACGCAAACATCCTTTACCTTTGTTCTTCTCAGCATGTAAATCTGGATCAGTTGCAACCACTGCACACACTCAGGAACCTGATGCACTTGGTCAGCCCGCGCTCTGAGCACCTAGGCAGGTCAGGACCCTTTGAAGTTCGAGCAGACCTCGAGGAGTCCATGGAATTTGTGGACCCTGGAGTTGCTGGAGAATCAGACGCGAGTGGAGATGAGCGTGTAAGCGATGAGGAGACTGACCTGGGCACAGACTGGGAGAACCTGCCAAGCCCCCGATTCTGTGACATCCCTTCCCAGCCGGTGGAAGTGTCCCAGAGCCAGAGCACCCAGGTGTCCCCACCCATTGCCAGCAGCAGCGCTGGTGGGATGATCTCCTCCGCTGCGGCCTCGGTGACCTCTTGGTTCAAAGCTTACACTGGACACCGCTAA');
INSERT INTO sequences VALUES (12, 'DNA', 'ENA|BAA14224|BAA14224.1 Streptomyces cacaoi beta-lactamase', 'ATGCGTATCCGTCCCACCCGTCGTCTTCTCCTCGGCGCGGTCGCGCCGCTCGCCCTCGTTCCGCTGGTGGCCTGCGGTCAGGCGTCGGGCTCCGAGAGCGGCCAGCAGCCCGGCCTCGGCGGTTGCGGGACGAGCGCACACGGCTCGGCGGACGCCCACGAGAAGGAGTTCCGGGCGCTGGAGAAGAAGTTCGACGCCCACCCTGGCGTCTACGCCATCGACACCCGCGACGGCCAGGAGATCACCCACCGGGCCGACGAGCGCTTCGCCTACGGCTCGACCTTCAAGGCCCTCCAGGCGGGCGCGATCCTTGCGCAAGTTCTCCGAGACGGGCGCGAAGTCCGGCGGGGCGCCGAGGCCGACGGCATGGACAAGGTGGTCCACTACGGGCAGGACGCGATCCTGCCCAACTCACCGGTGACCGAGAAGCACGTCGCGGACGGCATGTCCCTGCGCGAGCTGTGCGACGCCGTCGTGGCCTACAGCGACAACACCGCGGCCAACCTGCTCTTCGACCAGCTCGGCGGCCGAAGGGGCTCAACGCGGGTCCTCAAGCAGCTCGGCGACCACACCACGAGCATGGACCGCTACGAGCAGGAGCTGGGCTCGGCCGTCCCCGGCGACCCCCGGGACACCAGCACGCCGCGCGCGTTCGCCGAGGACCTGCGCGCCTTCGCCGTCGAGGACGGCGAGAAGGCCGCCCTCGCGCCCAACGACCGCGAGCAGCTGAACGACTGGATGAGCGGGAGCAGGACCGGCGACGCGCTGATCCGGGCCGGTGTGCCGAAGGACTGGAAGGTGGAGGACAAGAGCGGCCAGGTCAAGTACGGCACCCGGAACGACATCGCCGTCGTCCGCCCGCCCGGCCGCGCGCCGATCGTCGTCTCGGTGATGAGCCACGGCGACACCCAGGACGCCGAGCCGCACGACGAGCTGGTGGCCGAGGCCGGCCTCGTCGTCGCCGACGGTCTGAAGTGA');
INSERT INTO sequences VALUES (13, 'DNA', 'ENA|CAA25753|CAA25753.1 Bacillus cereus type 1 penicillinase', 'ATGATTTTGAAAAATAAGAGGATGCTAAAAATAGGAATATGCGTTGGTATATTAGGTTTAAGTATTACAAGCCTAGAAGCTTTTACAGGAGAGTCACTGCAAGTTGAAGCGAAAGAAAAGACTGGACAAGTGAAACACAAAAATCAGGCAACGCATAAAGAGTTCTCTCAACTTGAGAAAAAATTTGATGCTCGATTAGGTGTATATGCGATTGATACTGGTACAAATCAAACAATCTCTTATCGACCTAACGAAAGATTTGCCTTCGCATCAACATACAAGGCTTTAGCCGCGGGAGTATTACTACAGCAAAACTCAATTGATTCATTAAATGAAGTAATCACATATACGAAAGAAGACTTAGTGGATTATTCACCTGTTACAGAGAAACATGTAGATACTGGAATGAAACTAGGAGAAATTGCAGAGGCAGCTGTTCGTTCAAGTGATAATACTGCAGGGAACATTTTATTTAATAAAATAGGAGGACCGAAAGGATATGAAAAAGCGCTTAGGCATATGGGGGATCGGATTACTATGTCTAATCGCTTTGAAACAGAATTAAACGAAGCTATTCCAGGAGACATTCGTGACACTAGTACAGCGAAAGCTATTGCTACGAATCTTAAAGCTTTTACGGTCGGAAATGCACTTCCAGCTGAAAAACGTAAAATTCTTACAGAGTGGATGAAAGGAAATGCTACAGGGGACAAACTTATTAGAGCAGGCATACCAACTGACTGGGTAGTTGGAGATAAATCAGGTGCTGGTAGTTACGGGACAAGAAATGATATTGCTGTCGTTTGGCCTCCAAATAGTGCACCAATTATCGTATTAATTTCATCGAAAGATGAGAAAGAGGCAATCTATAATGATCAACTGATTGCGGAGGCAACTAAAGTTATAGTTAAAGGCTCTTAG');
INSERT INTO sequences VALUES (14, 'DNA', 'ENA|CAA27733|CAA27733.1 Staphylococcus aureus hypothetical protein', 'TTGAAAAAGTTAATATTTTTAATTGTAATTGCTTTAGTTTTAAGTGCATGTAATTCAAACAGTTCACATGCCAAAGAGTTAAATGATTTAGAAAAAAAATATAATGCTCATATTGGTGTTTATGCTTTAGATACTAAAAGTGGTAAGGAAGTAAAATTTAATTCAGATAAGAGATTTGCCTATGCTTCAACTTCAAAAGCGATAAATAGTGCTATTTTGTTAGAACAAGTACCTTATAATAAGTTAAATAAAAAAGTACATATTAACAAAGATGATATAGTTGCTTATTCTCCTATTTTAGAAAAATATGTAGGAAAAGATATCACTTTAAAAGCACTTATTGAGGCTTCAATGACATATAGTGATAATACAGCAAACAATAAAATTATAAAAGAAATCGGTGGAATCAAAAAAGTTAAACAACGTCTAAAAGAACTAGGAGATAAAGTAACAAATCCAGTTAGATATGAGATAGAATTAAATTACTATTCACCAAAGAGCAAAAAAGATACTTCAACACCTGCTGCCTTCGGTAAGACCCTTAATAAACTTATCGCCAATGGAAAATTAAGCAAAGAAAACAAAAAATTCTTACTTGATTTAATGTTAAATAATAAAAGCGGAGATACTTTAATTAAAGACGGTGTTCCAAAAGACTATAAGGTTGCTGATAAAAGTGGTCAAGCAATAACATATGCTTCTAGAAATGATGTTGCTTTTGTTTATCCTAAGGGCCAATCTGAACCTATTGTTTTAGTCATTTTTACGAATAAAGACAATAAAAGTGATAAGCCAAATGATAAGTTGATAAGTGAAACCGCCAAGAGTGTAATGAAGGAATTTTAA');