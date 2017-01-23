class Transition:
    def __init__(self, fromState, toState, label=unichr(955)):
        self.label = label
        self.toState = toState
        self.fromState = fromState

    def __str__(self):
        return 'Transition(' + self.fromState + ' ' + self.toState + ' ' + self.label + ")"

    def HasLambda(self):
        symb = unichr(955)
        if self.label == symb:
            return True
        return False

    def HasSameSourceAndSymbol(self, transition):
        thisValues = self.label.split(',')
        otherValues = transition.label.split(',')
        intersection = [val for val in thisValues if val in otherValues]

        if self.fromState == transition.fromState and len(intersection) > 0:
            return True
        return False

    def HasSameSourceAndValue(self, source, value):
        values = self.label.split(',')
        if source == self.fromState and value in values:
            return True

        return False

    def SetLabel(self, value):
        symb = unichr(955)
        if value == '':
            self.label = symb
        else:
            self.label = value
