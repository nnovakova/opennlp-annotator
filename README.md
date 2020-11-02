# OpenNLP Annotator

Annotates medical entities in the user provided text document via Apache OpenNLP.

## Configuration

1. Put text document of medical domain as `src/main/resources/txt/input-doc.txt` file
2. Optional: replace src/main/resources/entities.csv in case non-medical domain is used
3. Optional: adjust file paths in `io/github/nnovakova/annotator/Config.java`
## Run

Run the following command in shell:

```bash
gradle run
```

Result file in the current directory: `annotated_corpus.txt`



  