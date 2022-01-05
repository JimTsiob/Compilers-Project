def add(x,y):
   return x + y
   
print k
k = 0

# elegxos 2 : Klhsh mh dhlwmenhs synarthshs
a()

# elegxos xrhshs synarthshs prin th dhlwsh (gia 2o elegxo)
def a():
   print "hello!"

back()
# elegxos 3 : Lathos orismos orismatwn se synarthsh

def sub(x,y,z = 3):
   return x-y-z

add(1) # lathos periptwsh 1
add(1,3,4) # lathos periptwsh 2
add() # lathos periptwsh 3
a(1,3) # lathos periptwsh 4 (synarthsh pou den exei kan arguments pairnei argument lanthasmena apo ton user)
sub(1) # lathos periptwsh 5 gia na testaroume to default value.

# elegxos 4 : Xrhsh metavlhths allou typou ws akeraio

l = "Hello world"
print l + 2 

# periptwsh pou exoume syntheth arithmitikh parastash

print l + 3 + 2

# periptwsh me synarthsh 

def addition(x,y):
   return x + y
o = "hello world"
print addition(2,o)

# elegxos 5 : praxeis me None 

var = None
print var + 2

# elegxos 6 : Lathos tropos xrhshs synarthshs

def remove(x,y):
   return "hello"
print 2 * 2 + remove(2,1)

# elegxos 7 : epanalhpsh dhlwshs synarthshs me ton idio arithmo orismatwn

# 1o paradeigma (kanonikh periptwsh , dhlwsh synarthshs me idio onoma me idio arithmo orismatwn)
#def mul(x,y):
#  return x * y

#def mul(x,y):
#   return x * y

# 2o paradeigma (dhlwsh synarthshs me idio onoma me idio arithmo orismatwn kai ena extra defaul value)
def div(x,y):
   return x / y

def div(x,y,z = 1):
   return x / y / z

# 3o paradeigma (dhlwsh synarthshs me idio arithmo orismatwn , opou sth 2h synarthsh ena einai default)

def hello(x,y,z):
   return x + y * z

def hello(x,y,z=1):
   return x + y * z 

# 4o paradeigma (Omoia me panw apla 3 fores declaration)

def world(x = 1 ,y = 2):
   return x + y   

def world(x = 1 , y = 2):
   return x + y

def world(x = 1 , y = 2):
   return x + y