from graphviz import Digraph

from src.logic.state import StateType, StateColor


class GraphvizGenerator:
    def __init__(self):
        pass

    def GenerateGraphviz(self, graph):
        f = Digraph('finite_state_machine', format='png')
        f.body.extend(['rankdir=LR', 'size="8,5"'])

        for state in graph.states:
            if state.color == StateColor.NORMAL:
                self.configureStateColor(f, state, 'black', 'solid')
            elif state.color == StateColor.CURRENT:
                self.configureStateColor(f, state, 'azure3', 'filled')
            elif state.color == StateColor.VALID:
                self.configureStateColor(f, state, 'forestgreen', 'filled')
            elif state.color == StateColor.INVALID:
                self.configureStateColor(f, state, 'indianred3', 'filled')

        for transition in graph.transitions:
            f.edge(transition.fromState.label, transition.toState.label, transition.label)

        return f

    def configureStateColor(self, f, state, color, style):
        if state.type == StateType.NORMAL:
            f.node(state.label, shape='circle', style=style, color=color)
        elif state.type == StateType.END:
            f.node(state.label, shape='doublecircle', style=style, color=color)
        elif state.type == StateType.START:
            f.node(state.label, shape='circle', style=style, color=color)
            f.attr('node', shape='none')
            f.edge('', state.label)
            f.attr('node', shape='circle')
        elif state.type == StateType.START_END:
            f.node(state.label, shape='doublecircle', style=style, color=color)
            f.attr('node', shape='none')
            f.edge('', state.label)
            f.attr('node', shape='circle')
