* WordnetAPI *
Java API to the WordNet database of lexical relationships

WordnetAPI
|
|-- database: The H2 database files (they need to be in java classpath)
|   |-- path.db
|   |-- wordnet.27.log.db
|   |-- wordnet.data.db
|   |-- wordnet.index.db
|   `-- wordnet.trace.db
|
|-- ext: The thirdy-part libraries and versions
|   |-- h2.jar
|   |-- toplink-essentials.jar
|   `-- versions.txt
|
|-- lib: The core library for WordnetAPI
|   `-- wordnetapi.jar
|
|
`-- lgpl.txt: The license file
`-- wordnet.sh, wordnet.bat: 
        Simple unix/windows command-line executables to lookup in the dictionary


* Wordnet exec Example *

$ ./wordnet.sh  disk

LEMMA: disk (NOUN noun.artifact): a flat circular plate
SEMANTIC SET:
 SAME:
   disc (NOUN noun.artifact): a flat circular plate
 HYPERNYM:
   plate (NOUN noun.artifact): a sheet of metal or wood or glass or plastic
   circle (NOUN noun.artifact): any circular or rotating mechanism; "the machine punched out metal circles"
   round (NOUN noun.artifact): any circular or rotating mechanism; "the machine punched out metal circles"
 HYPONYM:
   puck (NOUN noun.artifact): a vulcanized rubber disk 3 inches in diameter that is used instead of a ball in ice hockey
   saucer (NOUN noun.artifact): a disk used in throwing competitions
   brake_disk (NOUN noun.artifact): a disk or plate that is fixed to the wheel; pressure is applied to it by the brake pads
   Frisbee (NOUN noun.artifact): a light, plastic disk about 10 inches in diameter; propelled with a flip of the wrist for recreation or competition
   diaphragm (NOUN noun.artifact): electro-acoustic transducer that vibrates to receive or produce sound waves
   phonograph_recording_disk (NOUN noun.artifact): a disk coated with cellulose acetate
   discus (NOUN noun.artifact): a disk used in throwing competitions
   deadeye (NOUN noun.artifact): (nautical) a round hardwood disk with holes and a grooved perimeter used to tighten a shroud
   coin_blank (NOUN noun.artifact): a flat metal disk ready for stamping as a coin
   hockey_puck (NOUN noun.artifact): a vulcanized rubber disk 3 inches in diameter that is used instead of a ball in ice hockey
   token (NOUN noun.artifact): a metal or plastic disk that can be redeemed or used in designated slot machines
   acetate_disk (NOUN noun.artifact): a disk coated with cellulose acetate
   planchet (NOUN noun.artifact): a flat metal disk ready for stamping as a coin
[...]
