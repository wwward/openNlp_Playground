wardwilliam_assignment_5 - www3@nyu.edu

1.  Compilation: javac -classpath ../../test_lib/opennlp-maxent-3.0.2-incubating.jar *.java

**  While working directory is parent path of org/wwward/*.class:

2.  execute modelmaker (2 arguments): java -cp test_lib/opennlp-maxent-3.0.2-incubating.jar:. org.wwward.Modelmaker <training file> <output file>

3.  execute tagger (3 arguments): java -cp test_lib/opennlp-maxent-3.0.2-incubating.jar:. org.wwward.Tagger <file_to_tag> <model file> <output file>

4.  Run scorer as normal.

..  Should take less than a couple minutes to execute either class.

..  Output is formatted with spaces rather than tabs to work for scorer.

