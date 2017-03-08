import json

from senticnet4 import senticnet

with open("sentic.json", "a") as myfile:
    myfile.write("{\n")
index = 0
for concept in sorted(senticnet.iterkeys()):
    result = {}
    print concept
    index += 1
    value = senticnet[concept]

    polarity = {}
    polarity['value'] = value[6]
    polarity['intensity'] = float(value[7])
    result['polarity'] = polarity

    result['moodTags'] = value[4:6]

    sentics = {"pleasantness": float(value[0]),
               "attention": float(value[1]),
               "sensitivity": float(value[2]),
               "aptitude": float(value[3])}
    result['sentics'] = sentics

    result['semantics'] = value[8:]

    with open("sentic.json", "a") as myfile:
        myfile.write('\"' + concept + '\":' + json.dumps(result))
        if index < 50000:
            myfile.write(',')
        myfile.write('\n')

with open("sentic.json", "a") as myfile:
    myfile.write("\n}")
