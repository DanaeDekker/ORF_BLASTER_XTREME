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
                "tool\tgene/orf\tseq\ttaxid\torganism"
                "\taccession\tprotein\tlength\texpect\tscore"
                "\tidentities\tpositives\tgaps\tsubject"
                "\tquery\tmatch\n")


def blast(file_name, header, seq, matrix_name, expect, hitlist_size):
    """ Function to BLAST the sequences.

    :param file_name: string - name of the file with the results
    :param header: string - contains the headers
    :param seq: string - contains the sequences
    :param matrix_name: string - name of the scorematrix
    :param expect: float - expect cutoff value
    :param hitlist_size: int - maximum number of results per BLAST
    """
    # Get the name of the ORF.
    header_split = str(header).split("|")[1]
    orf = header_split.split(":")[0]

    # Use BLAST.
    result_handle = NCBIWWW.qblast("blastp",
                                   "swissprot",
                                   seq,
                                   matrix_name=matrix_name,
                                   expect=expect,
                                   hitlist_size=hitlist_size)

    # Parse the result_handle.
    results = parse_xml(orf, seq, result_handle)

    # Write the results to the tsv-file.
    with open(file_name, "a") as file_open:
        for result in results:
            if result:
                file_open.write(result + "\n")


def parse_xml(orf, seq, result_handle):
    """ Parse the xml-file.

    :param orf: string - name of the ORF
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
            row = str("orffinder" + "\t" + str(orf)
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
    file_name = sys.argv[1]
    header = sys.argv[2]
    seq = sys.argv[3]
    matrix_name = sys.argv[4]
    expect = float(sys.argv[5])
    hitlist_size = int(sys.argv[6])

    # If neccesary, create the file that will contain the results.
    check_file(file_name)

    # Use BLAST on the sequence.
    blast(file_name, header, seq, matrix_name, expect, hitlist_size)
