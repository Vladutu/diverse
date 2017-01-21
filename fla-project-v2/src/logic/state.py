from enum import Enum


class StateType(Enum):
    NORMAL = 0
    START = 1
    END = 2
    START_END = 3


class State:
    def __init__(self, label, index, type=StateType.NORMAL):
        self.index = index
        self.type = type
        self.label = label

    def __str__(self):
        return "State(" + self.label + ")"

    def IsEndState(self):
        return self.type == StateType.END or self.type == StateType.START_END

    def IsStartState(self):
        return self.type == StateType.START or self.type == StateType.START_END

    def SetType(self, start, end):
        if start and end:
            self.type = StateType.START_END
        elif start and not end:
            self.type = StateType.START
        elif not start and end:
            self.type = StateType.END
        elif not start and not end:
            self.type = StateType.NORMAL

    def RemoveStartType(self):
        if self.type == StateType.START:
            self.type = StateType.NORMAL
        elif self.type == StateType.START_END:
            self.type = StateType.END
