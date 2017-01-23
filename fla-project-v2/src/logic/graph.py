from src.logic.graphvizGenerator import GraphvizGenerator
from src.logic.state import State, StateType, StateColor
from src.logic.transition import Transition


class Graph:
    def __init__(self):
        self.states = []
        self.transitions = []
        self.generator = GraphvizGenerator()

    def AddState(self, state):
        self.states.append(state)

    def AddStateFromName(self, stateName, index):
        state = State(label=stateName, index=index)
        self.AddState(state)

    def AddStates(self, states):
        for state in states:
            self.AddState(state)

    def AddTransition(self, transition):
        self.transitions.append(transition)

    def AddTransitions(self, transitions):
        for transition in transitions:
            self.AddTransition(transition)

    def __str__(self):
        return 'Graph(' + str(self.states) + ' ' + str(self.transitions) + ")"

    def FindStateByName(self, stateName):
        for state in self.states:
            if state.label == stateName:
                return state
        return None

    def DeleteStateFromName(self, stateName):
        state = self.FindStateByName(stateName)
        if state is None:
            return None

        transitionsToBeRemoved = []
        for transition in self.transitions:
            if transition.fromState == state or transition.toState == state:
                transitionsToBeRemoved.append(transition)
        for transition in transitionsToBeRemoved:
            self.transitions.remove(transition)

        self.states.remove(state)
        return state

    def AddTransitionFromNames(self, fromValue, toValue, value):
        fromState = self.FindStateByName(fromValue)
        toState = self.FindStateByName(toValue)

        if fromState is None or toState is None:
            return None

        transition = None
        if value == '':
            transition = Transition(fromState, toState)
        else:
            transition = Transition(fromState, toState, value)

        existingTransition = self.FindTransitionFromStates(fromState, toState)

        if existingTransition is not None:
            existingTransition.SetLabel(value)
            return existingTransition

        self.AddTransition(transition)

        return transition

    def DeleteTransitionFromNames(self, fromValue, toValue):
        fromState = self.FindStateByName(fromValue)
        toState = self.FindStateByName(toValue)

        if fromState is None or toState is None:
            return None

        transition = self.FindTransitionFromStates(fromState, toState)

        if transition is None:
            return None

        self.transitions.remove(transition)
        return transition

    def FindTransitionFromStates(self, fromState, toState):
        for transition in self.transitions:
            if transition.fromState == fromState and transition.toState == toState:
                return transition

        return None

    def HasStartState(self):
        for state in self.states:
            if state.type == StateType.START or state.type == StateType.START_END:
                return True
        return False

    def TestInput(self, input):
        currentState = self.FindStartState()
        complete = True

        for c in input:
            for transition in self.transitions:
                if transition.fromState == currentState:
                    values = transition.label.split(',')
                    if c in values:
                        currentState = transition.toState
                        break
            else:
                complete = False

        if currentState.IsEndState() and complete is True:
            return True
        return False

    def FindStartState(self):
        for state in self.states:
            if state.type == StateType.START or state.type == StateType.START_END:
                return state
        return None

    def IsNFA(self):
        for transition in self.transitions:
            if transition.HasLambda():
                return True

        for t1 in self.transitions:
            for t2 in self.transitions:
                if t1 == t2:
                    continue
                if t1.HasSameSourceAndSymbol(t2):
                    return True

        return False

    def FindNextState(self, currentState, value):
        for transition in self.transitions:
            if transition.HasSameSourceAndValue(currentState, value):
                return transition.toState
        return None

    def GenerateImage(self):
        diGraph = self.generator.GenerateGraphviz(self)
        diGraph.render('../resources/fsm')

    def SetDefaultColor(self):
        for state in self.states:
            state.SetColor(StateColor.NORMAL)
