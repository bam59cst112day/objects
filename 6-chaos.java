//////// Arrays of numbers; arrays of objects:  Balls, Birds, and Trees.

int[] list=  { 99,44, 22, 33, 77, 4 };

Tree t1, t2, t3;
Tree[] forest;
int treeSpacing=  40;
int treeMany;

Ball a,b,c,d,e;
Ball[] bunch;
int manyBalls=15;
int score=0;

Bird hawk, oriole, jay;
Bird[] flock;
int manyBirds;


String s="";

float horizon;

void setup() {
  size(750, 500 );
  horizon=  height/4;

  //// Trees:  3 individual trres at bottom + an array of trees at horizon.
  t1=  new Tree( 100,height-90, 40, 80 );
  t2=  new Tree( 200,height-25, 60, 90 );
  t3=  new Tree( 300,height-50, 30, 70 );

  treeMany=  1 + width / treeSpacing;
  forest=  new Tree[ treeMany ];

  float treeX=20;
  int i=0;
  while (treeX < width) {
    forest[i]=  new Tree( 
      random( treeX-10, treeX+10),           // Position
      random( horizon+50, horizon-5), 
      random( 30,70 ),                     // Width of leaves.
      random( 50,90 )                      // Height of trunk
    );
    treeX += treeSpacing;
    i++;
  }



  //// Balls:  3 individuals + an array of many.
  a=  new Ball( "a", 255,255,0 );
  b=  new Ball( "b", 0,255,0 );
  c=  new Ball( "c", 255,0,255  );
  //
  bunch=  new Ball[manyBalls];
  for( int j=0; j<manyBalls; j++) {
    bunch[j]=  new Ball( ""+j );    
  }

  // Birds!
  hawk=  new Bird();
  oriole=  new Bird();
  oriole.r=255;
  oriole.g=150;
  jay=  new Bird();
  jay.b=255;
  //
  manyBirds=  int( random(1,20) );
  resetBirds( manyBirds );
  //
  reset();
}
void reset() {
  a.reset();
  b.reset();
  c.reset();
}
void resetBirds( int m ) {
  flock=  new Bird[m];
  for( int j=0; j<m; j++) {
    flock[j]=  new Bird( random(0,horizon) );    
  }
}  



//// NEXT FRAME:  scene, birds, balls.
void draw() {
  scene();
  birds();
  balls();
  //
  //// Change size of flock!!!!
  if ( flock[0].x > width+200 ) {
    manyBirds=  int(random( 3,20 ));
    resetBirds( manyBirds );
  }
  text( manyBirds+" birds.", width*2/4, 20 );
  //
  text( s, width/2, 20 );
  //  Add up the numbers, and get the average.
  int many=  list.length;
  int total=0;
  for( int i=0; i<many; i++ ) {
    total += list[i];
  }
  text( "Total is:    "+total, 10,10 );
  text( "Average is:  "+total/many, 10,20 );

}

//// SCENE:  sky & grass.
void scene() {
  background(150,200,250);
  fill( 150,250,150 );
  rect( 0,horizon, width, height-horizon );
  fill(255,200,0);
  noStroke();
  ellipse( frameCount%width, horizon-40-sin( (PI*frameCount/width) % PI )*horizon/2, 40, 40 );
  stroke(0);
  // Trees.
  t1.show();
  t2.show();
  t3.show();
  //
  for( int i=0; i<forest.length; i++) {
    forest[i].show();
  }
    
}

//// Move and show birds
void birds() {
  hawk.move();
  oriole.move();
  jay.move();
  //
  hawk.show();
  oriole.show();
  jay.show();
  // Flock of birds
  for( int i=0; i<manyBirds; i++) {
    flock[i].move();
    flock[i].show();
  }
}

//// Move & show each ball
void balls() {
  a.move();
  b.move();
  c.move();
  collision( a, b );
  collision( a, c );
  collision( b, c );
  //
  a.show();
  b.show();
  c.show();
  // Bunch of balls
  for( int i=0; i<manyBalls; i++) {
    bunch[i].move();
    bunch[i].show();
  }
  
}

//// Elastic collisions.
void collision( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;      // Swap the velocities.
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp;
  }
  //// Check all collisions (within the bunch).
  // NESTED LOOPS //
  int m=  bunch.length;
  for( int j=0; j<m; j++){
    //// Check each ball ONLY against lower ones in the bunch.
    for( int k=j+1; k<m; k++ ){
      elastic( bunch[j], bunch[k] );
      score++;
    }
  }
}
//// Check for collision; if so, swap velocities.
void elastic( Ball one, Ball two ) {
  if( one.hit( two.x, two.y ) ) {
    swap( one, two );
  }
}
void swap( Ball p, Ball q ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;      // Swap the velocities.
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp;
}

//// HANDLERS:  keys & clicks.
void keyPressed() {
  if (key == 'q') exit();
  if (key == 'a') a.reset();
  if (key == 'b') b.reset();
  if (key == 'c') c.reset();
  if (key == 'r') {
        a.reset();        b.reset();        c.reset();
        hawk.reset();     oriole.reset();   jay.reset();
  }
  if (key == 'A') {
    a.dx *= 2;          // Make a ball go faster!
    a.dy *= 2;
  }
  if (key == 'h') { hawk.reset(); }
  if (key == 'i') { oriole.reset(); }
  if (key == 'j') { jay.reset(); }
}
void mousePressed() {
  if ( hawk.hit( mouseX,mouseY ) ) {  hawk.reset(); }
  if ( oriole.hit( mouseX,mouseY ) ) {  oriole.reset(); }
  if ( jay.hit( mouseX,mouseY ) ) {  jay.reset(); }
  //
  if ( a.hit( mouseX,mouseY ) ) {  a.reset(); }
  if ( b.hit( mouseX,mouseY ) ) {  b.reset(); }
  if ( c.hit( mouseX,mouseY ) ) {  c.reset(); }
}
    


//// OBJECTS ////
class Ball {
  //// PROPERTIES:  position, speed, color, etc. ////   (What a Ball "has".)
  float x,y, dx,dy;
  int r,g,b;
  String name="";
  
  //// CONSTRUCTORS (if any). ////
  Ball( String s ) { 
    this.name=  s;
    this.r=  int( random(255) );
    this.g=  int( random(255) );
    this.b=  int( random(255) );
    reset(); 
  }
  Ball( String s, int r, int g, int b ) { 
    this.name=  s;  
    this.r=  r;  
    this.g=  g;  
    this.b=  b;  
    reset(); 
  }
  Ball( float x, float y ){    this.x=x;  this.y=y;  }
  Ball( float x, float y, float dx, float dy ){    this.x=x;  this.y=y;  this.dx=dx;  this.dy=dy;  }
  Ball( String s, float x, float y ) {    this.name=  s;  this.x=x;  this.y=y;  this.dx=dx;  this.dy=dy;  }
  Ball( String s, float x, float y, float dx, float dy ) {    this.name=  s;  this.x=x;  this.y=y;  this.dx=dx;  this.dy=dy;  }
  
  //// METHODS:  show, move, detect a "hit", etc. ////  (What a Ball "does".)
  void show() {
    fill(r,g,b);
    ellipse( x,y, 30,30 );
    fill(0);
    text( name, x-5,y );
  }
  void move() {
    if (x>width || x<0) {  dx=  -dx; }
    if (y>height || y<horizon) {  dy=  -dy; }
    x=  x+dx;
    y=  y+dy;
  }
  void reset() {
    x=  random( width/2, width-100 );
    y=  random( horizon+0, height-50 );
    dx=  random( 1,5 );
    dy=  random( 1,3 );
  }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;
    else return false;
  }
}

class Bird {
  //// PROPERTIES:  position, speed, color, etc. ////   (What a Bird "has".)
  float x=random(0,300), y=random(50,150), dx=5,dy=0.5;
  float w=60;
  int r,g,b;
  int number;
  boolean wingUp=false;
  
  //// CONSTRUCTORS (if any). ////
  Bird() {}
  Bird( float y ) {
    this.y=  y;
    r=  int( random(255) );        // Random color.
    g=  int( random(255) );
    b=  int( random(255) );
  }
  
  //// METHODS:  show, move, detect a "hit", etc. ////  (What a Ball "does".)
  void show() {
    fill(r,g,b);
    triangle( x,y, x-w,y-10, x-w,y+10 );
    // Wing
    wingUp=  int(frameCount/30) %2 >0;
    fill(255);
    if (wingUp) {
      triangle( x-w/3,y, x-w*2/3,y, x-w/2,y-40 );
    }else{
      triangle( x-w/3,y, x-w*2/3,y, x-w/2,y+40 );
    }
  }
  void move() {
    x=  x+dx;
    y=  y+dy;
    if (x>horizon) {
      dy=  -dy;             // Bounce up from grass!
    }
  }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;
    else return false;
  }
  void reset() {
      x=0;
      y=  random( 50, horizon-30 );
      dx=  random( 2,5 );
      //
      
  }
}


class Tree{
  float x, y;            // Position of base.
  float trunk, leaves;   // Width of top, height of trunk;
  float r=100, g=250, b=100;
  // CONSTRUCTOR //
  Tree( float x, float y, float leaves, float trunk ){
    this.x=x;  
    this.y=y + random(-30, +80 );
    this.leaves=  leaves;
    this.trunk=trunk;
    colorTree();
  }
  void colorTree() {
    r +=  random(-50,+50);
    g +=  random(-50,+50);
    b +=  random(-50,+50);
  }
  // METHODS //
  void show() {
    fill(150,0,0);          // Brown
    rect(x,y, leaves/6,-trunk );
    fill( r,g,b );
    ellipse( x, y-trunk, leaves, leaves );
  }
}


