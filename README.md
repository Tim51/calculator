##Purpose of program##

A program made for testing the NETBeans GUI builder.

##Features and improvements:##

* Efficient algorithm, allowing fast processing of large expressions.
* Enter expressions using onscreen-buttons or with keyboard.
* Don't think I implement exception handling, so could improve by adding.
* GUI is not well organised, could improve by reducing spacing add implmenting a 'simple mode'.

##How the algortithm works:##

1. Simplifies words such as cos and sin into single letters.
2. Divide expression into two lists, operators and numbers. Order is kept.
3. Add pointer to operator list.
4. Move pointer forwards until closed parenthesis is found. Call this position A.
5. Move pointer backwards until open parenthesis is found. Call this position B.
6. Compute expression between A and B.
7. Repeat from step 4 until no closed parenthesis found.
8. Compute full expression to obtain a single number.
