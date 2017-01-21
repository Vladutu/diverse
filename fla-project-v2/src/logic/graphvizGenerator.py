from graphviz import Digraph

from src.logic.state import StateType


class GraphvizGenerator:
    def __init__(self):
        pass

    def GenerateGraphviz(self, graph):
        f = Digraph('finite_state_machine', format='png')
        f.body.extend(['rankdir=LR', 'size="8,5"'])

        for state in graph.states:
            if state.type == StateType.NORMAL:
                f.node(state.label, shape='circle')
            elif state.type == StateType.END:
                f.node(state.label, shape='doublecircle')
            elif state.type == StateType.START:
                f.node(state.label, style='filled', shape='circle')
                f.attr('node', shape='none')
                f.edge('', state.label)
                f.attr('node', shape='circle')
            elif state.type == StateType.START_END:
                f.node(state.label, style='filled', shape='doublecircle')
                f.attr('node', shape='none')
                f.edge('', state.label)
                f.attr('node', shape='circle')

        for transition in graph.transitions:
            f.edge(transition.fromState.label, transition.toState.label, transition.label)

        return f
