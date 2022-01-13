def add(x,y,z):
   return x + y

def add(x,y,z=1):
   return x-y-z

def add(x,y):
   return x + y

def add(x,y,z=1):
   return x-y-z

# 1o paradeigma (dhlwsh synarthshs me idio onoma me idio arithmo orismatwn kai ena extra default value)
def div(x,y):
   return x / y

def div(x,y,z = 1):
   return x / y / z

# 2o paradeigma (dhlwsh synarthshs me idio arithmo orismatwn , opou sth 2h synarthsh ena einai default)

def hello(x,y,z):
   return x + y * z

def hello(x,y,z=1):
   return x + y * z 

# 3o paradeigma (Omoia me panw apla 3 fores declaration)

def world(x = 1 ,y = 2):
   return x + y   

def world(x = 1 , y = 2):
   return x + y

def world(x = 1 , y = 2):
   return x + y