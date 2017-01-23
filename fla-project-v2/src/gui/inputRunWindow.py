from wx import wx

from src.gui.simulationWindow import SimulationWindow


class InputRunWindow(wx.Frame):
    def __init__(self, graph, controller, parent=None):
        super(InputRunWindow, self).__init__(parent, size=(200, 200))
        self.graph = graph
        self.controller = controller
        self.initUI()
        self.BindEvents()

        self.Center()
        self.Show()

    def initUI(self):
        self.controller.Disable()
        self.panel = wx.Panel(self)
        self.runButton = wx.Button(self.panel, label="Run")
        self.runLabel = wx.StaticText(self.panel, label="Input:")
        self.resultLabel = wx.StaticText(self.panel)
        self.simulationButton = wx.Button(self.panel, label="Step by step simulation")
        self.input = wx.TextCtrl(self.panel, size=(140, -1))

        self.windowSizer = wx.BoxSizer()
        self.windowSizer.Add(self.panel, 1, wx.ALL | wx.EXPAND)

        self.sizer = wx.GridBagSizer(5, 5)
        self.sizer.Add(self.runLabel, (0, 0))
        self.sizer.Add(self.input, (0, 1))
        self.sizer.Add(self.resultLabel, (1, 0))
        self.sizer.Add(self.runButton, (2, 0))
        self.sizer.Add(self.simulationButton, (2, 1))

        self.border = wx.BoxSizer()
        self.border.Add(self.sizer, 1, wx.ALL | wx.EXPAND, 5)

        self.panel.SetSizerAndFit(self.border)
        self.SetSizerAndFit(self.windowSizer)

        if not self.graph.HasStartState():
            self.runButton.Disable()
            self.input.Disable()
            self.simulationButton.Disable()
            self.resultLabel.SetLabel('No Start State')
        else:
            self.runButton.Disable()
            self.simulationButton.Disable()
            self.resultLabel.SetLabel('No input')

    def BindEvents(self):
        self.Bind(wx.EVT_CLOSE, self.OnClose)
        self.runButton.Bind(wx.EVT_BUTTON, self.OnRun)
        self.input.Bind(wx.EVT_CHAR, self.OnInputChange)
        self.simulationButton.Bind(wx.EVT_BUTTON, self.OnSimulation)

    def OnSimulation(self, event):
        simulationWindow = SimulationWindow(self, self.graph, self.input.GetValue())

    def OnInputChange(self, event):
        kc = chr(event.GetKeyCode())
        label = self.input.GetValue()

        if kc == '\b':
            label = label[:-1]
        else:
            label = label + kc

        if label == '':
            self.runButton.Disable()
            self.simulationButton.Disable()
            self.resultLabel.SetLabel('No input')
        else:
            self.runButton.Enable()
            self.simulationButton.Enable()
            self.resultLabel.SetLabel('')
        event.Skip()

    def OnRun(self, event):
        input = self.input.GetValue()
        result = self.graph.TestInput(input)

        if result:
            self.resultLabel.SetLabel('Input accepted')
        else:
            self.resultLabel.SetLabel('Input rejected')

    def OnClose(self, event):
        self.controller.Enable()
        self.Destroy()

    def DisableWidgets(self):
        self.runButton.Disable()
        self.simulationButton.Disable()
        self.input.Disable()

    def EnableWidgets(self):
        self.runButton.Enable()
        self.simulationButton.Enable()
        self.input.Enable()
