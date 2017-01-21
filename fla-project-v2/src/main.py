from wx import wx

from src.gui.window import Window

if __name__ == '__main__':
    app = wx.App()
    Window(None)
    app.MainLoop()
'''

from src.logic.graph import Graph
from src.logic.graphvizGenerator import GraphvizGenerator
from src.logic.state import StateType, State
from src.logic.transition import Transition

graph = Graph()

s1 = State('A', type=StateType.END, index=1)
s2 = State('B', type=StateType.START, index=2)
s3 = State('C', index=3)
s4 = State('D', type=StateType.END, index=4)
s5 = State('E', index=5)

graph.AddStates([s1, s2, s3, s4, s5])

t1 = Transition(s1, s2, label='1')
t2 = Transition(s1, s3)
t3 = Transition(s5, s3, label='0')
t4 = Transition(s4, s3)

graph.AddTransitions([t1, t2, t3, t4])

generator = GraphvizGenerator()
digraph = generator.GenerateGraphviz(graph)

digraph.render('fsm')
'''
