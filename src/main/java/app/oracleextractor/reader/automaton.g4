grammar automaton;

/* digraph Automaton {
       node [shape=point] INIT;
       q1 [shape="doublecircle"];
       node [shape=circle];
       rankdir = LR;
       INIT -> q1;
       q1 -> q2 [label="b/2"];
       q1 -> q4 [label="b/8"];
       q1 -> q4 [label="a/1"];
       q2 -> q1 [label="a/3"];
       q2 -> q3 [label="b/2"];
       q3 -> q2 [label="a/1"];
       q3 -> q1 [label="b/2"];
       q4 -> q1 [label="b/1"];
       q4 -> q3 [label="a/2"];
       q4 -> q2 [label="a/2"];
   }
*/


automatonGraph: 'digraph' AUTOMATON_NAME '{' initArrow statements* '}'<EOF>;

statements: STATE_NAME '->' STATE_NAME '[label=' trigger_output '];';

trigger_output: '"' TRIGGER '/' OUTPUT '"';

initArrow: 'INIT -> ' STATE_NAME ';';


TRIGGER: CHAR;
OUTPUT: CHAR | NUM;
WS: [ \t\r\n] -> skip;
CHAR: [a-z];
NUM: [0-9];
STATE_NAME: [a-z0-9]+;
AUTOMATON_NAME: [A-Za-z]+;
POINT_TO_INITIAL: 'node [shape=point] INIT;' -> skip;
CURRENT_FORM: STATE_NAME ' [shape="doublecircle"];' -> skip;
CIRCULAR_SHAPE_STATES: 'node [shape=circle];' -> skip;
GRAPH_ORIENTATION: 'rankdir = LR;' -> skip;

