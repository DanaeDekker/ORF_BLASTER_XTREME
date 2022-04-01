# auteurs: Marco Veninga, Stef van Breemen, Martine Rijploeg, Danae Dekker
# klas: bin2b, groep 6
# datum: 31MRT2022
# known bugs: none

# IMPORTANT!
# - Install Biopython before using the script.
# - Header should be a single word without any symbols like > or |.
# - Call the script in Bash using:
#   python3 blaster.py output_file.tsv header seq db matrix_name expect 
#   word_size

from Bio.Blast import NCBIWWW
from Bio.Blast import NCBIXML
from Bio import Entrez
import re
import sys


def check_file(file_name):
    """ Check if the file exist and create one if it doesn't.
    :param file_name: string - name of the file
    """
    # Define the variables.
    try:
        file_open = open(file_name, "r")
        file_open.close()
    except FileNotFoundError:
        with open(file_name, "w") as file_open:
            file_open.write(
                "tool\tid\tseq\ttaxid\torganism"
                "\taccession\tprotein\tlength\texpect\tscore"
                "\tidentities\tpositives\tgaps\tsubject"
                "\tquery\tmatch\n")


def blast(file_name, header, seq, db, matrix_name, expect, word_size):
    """ Function to BLAST the sequences.
    :param file_name: string - name of the file with the results
    :param header: string - contains the name of the header
    :param seq: string - contains the sequences
    :param db: string - name of the database
    :param matrix_name: string - name of the scorematrix
    :param expect: float - expect cutoff value
    :param word_size: int - word size
    """
    # Use BLAST.
    result_handle = NCBIWWW.qblast("blastp",
                                   db,
                                   seq,
                                   matrix_name=matrix_name,
                                   expect=expect,
                                   word_size=word_size,
                                   hitlist_size=10)

    # Parse the result_handle.
    results = parse_xml(header, seq, result_handle)

    # Write the results to the tsv-file.
    with open(file_name, "a") as file_open:
        for result in results:
            if result:
                file_open.write(result + "\n")


def parse_xml(header, seq, result_handle):
    """ Parse the xml-file.
    :param header: string - name of the ORF
    :param seq: string - sequence
    :param result_handle - result handle of the BLAST
    """
    # Define the variables.
    values = []
    rows = []

    # Open the xml-file and read it using Biopython's parse-function.
    blast_record = NCBIXML.read(result_handle)

    # Add the alignment-data to a list. Each list contains ten lists
    # with alignments, or less if less alignments were found.
    for alignment in blast_record.alignments:
        for hsp in alignment.hsps:
            values.append(alignment.title)
            values.append(alignment.accession)
            values.append(hsp.align_length)
            values.append(hsp.expect)
            values.append(hsp.score)
            values.append(hsp.identities)
            values.append(hsp.positives)
            values.append(hsp.gaps)
            values.append(hsp.sbjct)
            values.append(hsp.query)
            values.append(hsp.match)

        if values:
            # Find the scientific name of the species in the title.
            title = str(values[0]).split("[")
            species = title[len(title) - 1]
            scientific = species.replace("]", "")

            # Call the functions to get the neccesary data.
            taxid = get_taxid(scientific)
            protein = get_protein(values[0])

            print(scientific + " | " + protein)

            # Append the results.
            row = str("orfipy" + "\t" + str(header)
                      + "\t" + str(seq) + "\t" + str(taxid) +
                      "\t" + str(scientific) + "\t" + str(values[1]) +
                      "\t" + str(protein) + "\t" + str(values[2]) +
                      "\t" + str(values[3]) + "\t" + str(values[4]) +
                      "\t" + str(values[5]) + "\t" + str(values[6]) +
                      "\t" + str(values[7]) + "\t" + str(values[8]) +
                      "\t" + str(values[9]) + "\t" + str(values[10]))

            # Append the rows.
            rows.append(row)

            # Empty the list with values.
            values = []

    # Return the lists.
    return rows


def get_taxid(scientific):
    """ Get the taxid and lineage of an organism.
    :param scientific: string - scientific name of the organism
    :return taxid_lineage: string - contains the taxid, domain, genus
    and species of the organism
    """
    # Extract the taxonomy from the Entrez-database.
    handle = Entrez.esearch(db="Taxonomy", term=scientific)
    record = Entrez.read(handle)
    taxid = record["IdList"][0]

    # Return the string.
    return taxid


def get_protein(title):
    """ Get the accession and the name of a protein.
    :param title: string - contains the name of the protein and the
    scientific name of the organism
    :return protein: string - contains the name of the protein
    """
    # Find the name of the protein.
    full = re.search(r"Full=(.*)", title)
    match = full.group(0)
    if ";" in match:
        protein = match.split(";")[0]
    else:
        protein = match.split("[")[0]
    protein = protein.replace("Full=", "")

    # Return the string.
    return protein


if __name__ == '__main__':
    # Define the variables.
    file_name = str(sys.argv[1])
    header = str(sys.argv[2])
    seq = str(sys.argv[3])
    db = str(sys.argv[4])
    matrix_name = str(sys.argv[5])
    expect = float(sys.argv[6])
    word_size = int(sys.argv[7])

    # If neccesary, create the file that will contain the results.
    check_file(file_name)

    # Use BLAST on the sequence.
    blast(file_name, header, seq, db, matrix_name, expect, word_size)
