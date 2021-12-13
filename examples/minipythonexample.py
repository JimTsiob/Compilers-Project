# minipython example
# also testing Module ::= ( Identifier "." ) * Identifier 
from math import sqrt # testing "from" Module "import" Identifier ("as" Identifier)? ( "," Identifier ("as" Identifier)? )*
import numpy as np, matplotlib as plt # testing "import" Module ("as" Identifier)? ( "," Module ("as" Identifier)? )*


# testing Function rule
def addOrMax(x, y = 1):
    if x - y >= 10 :
        return max(x.y(), 1) # testing "return" Expression
    return x+y

# testing Function Call rule
print addOrMax(5, 7)

print min(5,4)

# testing Identifier = Expression  
list = [1,2,3]

def whileAndFor():
    list = [1, 2, 3]
    for item in list: # testing "for" Identifier "in" Identifier ":" Statement
        print len(list) ** item % 10
    
    _x = 5
    while not x != 5 or x < 7 and x >= 3: # testing "while" Comparison ":" Statement , and , or , not operators
        _x -= 1
        _x /= 5

    if list[0] == 10:  
        print 'true' 
    if list[0] != 10:
        print 'false' 

list[5] = 3 # testing  Identifier "[" Expression "]" "=" Expression
# anathesh symboloseiras se metavlhth
word = "hello friends!" 
# anathesh akeraiou arithmou se metavlhth
intNum = 10 
# anathesh dekadikou arithmou se metavlhth
floatNum = 9.5 
# anathesh listas se metavlhth
testList = ["apple","banana","orange"]
stringLit = 'yo'
a0 = 3**2 + 7*8%2 # testing arithmetic operator priority

assert x, list[5] # testing "assert" Expression ("," Expression )?

# example gia value 
print a0.addOrMax(5,10) 