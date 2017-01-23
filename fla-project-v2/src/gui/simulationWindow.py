from wx import wx

from src.logic.state import StateColor


class SimulationWindow(wx.Frame):
    def __init__(self, controller, graph, input, parent=None):
        super(SimulationWindow, self).__init__(parent, size=(200, 200), title='Simulation window')
        self.controller = controller
        self.mainController = controller.controller
        self.graph = graph
        self.input = input
        self.InitUI()
        self.InitializeGraph()
        self.BindEvents()

        self.Center()
        self.Show()

    def InitUI(self):
        self.controller.DisableWidgets()
        self.panel = wx.Panel(self)
        self.defaultColor = self.panel.GetBackgroundColour()
        self.stepButton = wx.Button(self.panel, label="Step")
        self.inputTextBoxes = []
        self.inputSizer = wx.GridBagSizer()
        self.btnSizer = wx.GridBagSizer()
        self.index = 0
        self.resultLabel = wx.StaticText(self.panel)
        self.font = wx.Font(18, wx.DECORATIVE, wx.NORMAL, wx.BOLD)
        self.windowSizer = wx.BoxSizer()
        self.windowSizer.Add(self.panel, 1, wx.ALL | wx.EXPAND)
        self.sizer = wx.GridBagSizer(5, 5)

        i = 0
        for c in self.input:
            newTextBox = wx.StaticText(self.panel)
            newTextBox.SetLabel(c)
            newTextBox.SetFont(self.font)
            newTextBox.SetForegroundColour('Black')
            self.inputTextBoxes.append(newTextBox)
            self.inputSizer.Add(newTextBox, (0, i))
            i += 1

        self.sizer.Add(self.inputSizer, (0, 0))
        self.btnSizer.Add(self.stepButton, (0, 0))
        self.sizer.Add(self.resultLabel, (1, 0))
        self.sizer.Add(self.btnSizer, (2, 0))

        self.border = wx.BoxSizer()
        self.border.Add(self.sizer, 1, wx.ALL | wx.EXPAND, 5)

        self.panel.SetSizerAndFit(self.border)
        self.SetSizerAndFit(self.windowSizer)

    def InitializeGraph(self):
        self.currentState = self.graph.FindStartState()
        self.currentState.SetColor(StateColor.CURRENT)
        self.mainController.Refresh()

    def BindEvents(self):
        self.Bind(wx.EVT_CLOSE, self.OnClose)
        self.stepButton.Bind(wx.EVT_BUTTON, self.OnStep)

    def OnClose(self, event):
        self.controller.EnableWidgets()
        self.graph.SetDefaultColor()
        self.mainController.Refresh()
        self.Destroy()

    def OnStep(self, event):
        currentTextBox = self.inputTextBoxes[self.index]
        currentTextBox.SetBackgroundColour('White')
        currentTextBox.SetForegroundColour('Red')
        if self.index > 0:
            prevTextBox = self.inputTextBoxes[self.index - 1]
            prevTextBox.SetBackgroundColour(self.defaultColor)
            prevTextBox.SetForegroundColour('Black')

        self.currentState.SetColor(StateColor.NORMAL)
        nextState = self.graph.FindNextState(self.currentState, currentTextBox.GetLabel())

        if nextState is not None:
            self.currentState = nextState
            self.currentState.SetColor(StateColor.CURRENT)
            if self.index == len(self.inputTextBoxes) - 1:
                if self.currentState.IsEndState():
                    self.currentState.SetColor(StateColor.VALID)
                else:
                    self.currentState.SetColor(StateColor.INVALID)
                self.stepButton.Disable()
        else:
            self.currentState.SetColor(StateColor.INVALID)
            self.stepButton.Disable()
        self.index += 1

        self.mainController.Refresh()
        self.Refresh()
