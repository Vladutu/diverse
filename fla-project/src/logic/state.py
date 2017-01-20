from enum import Enum


class StateType(Enum):
    Normal = 0
    Start = 1
    End = 2


class State:
    def __init__(self, position, index, name, radius=50, selected=False, type=StateType.Normal):
        self.name = name
        self.index = index
        self.radius = radius
        self.position = position
        self.selected = selected
        self.type = type
