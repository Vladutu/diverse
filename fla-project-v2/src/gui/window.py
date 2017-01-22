import pickle

import wx

from src.gui.inputRun import InputRunWindow
from src.logic.graph import Graph
from src.logic.graphvizGenerator import GraphvizGenerator
from src.logic.state import StateType


class Window(wx.Frame):
    def __init__(self, parent):
        super(Window, self).__init__(parent, title="FLA project", size=(900, 700))
        self.InitLogic()
        self.InitUI()

    def CreateInitialImage(self):
        diGraph = self.generator.GenerateGraphviz(self.graph)
        diGraph.render('../resources/fsm')
        image = wx.Image('../resources/fsm.png', wx.BITMAP_TYPE_ANY)
        self.graphImage = wx.StaticBitmap(self.imgPanel, wx.ID_ANY, wx.BitmapFromImage(image))

    def InitLogic(self):
        self.graph = Graph()
        self.stateIndex = 0
        self.reusableIndexes = []
        self.generator = GraphvizGenerator()
        self.names = []
        self.currentStateName = ''

    def InitUI(self):
        self.CreateMenu()
        self.CreateBody()
        self.BindEvents()
        self.Center()
        self.Show()

    def CreateMenu(self):
        self.menu = wx.Menu()

        menuBar = wx.MenuBar()
        fileMenu = wx.Menu()

        self.newFItem = wx.MenuItem(fileMenu, 123, '&New')
        self.newFItem.SetBitmap(wx.Bitmap('../resources/Files-New-File-icon.png'))
        fileMenu.AppendItem(self.newFItem)

        self.openFItem = wx.MenuItem(fileMenu, 212, '&Open')
        self.openFItem.SetBitmap(wx.Bitmap('../resources/open-file-icon.png'))
        fileMenu.AppendItem(self.openFItem)

        self.saveFItem = wx.MenuItem(fileMenu, 353, '&Save')
        self.saveFItem.SetBitmap(wx.Bitmap('../resources/save-file-icon.png'))
        fileMenu.AppendItem(self.saveFItem)

        toolsMenu = wx.Menu()
        self.runFItem = wx.MenuItem(toolsMenu, 1654, '&Run input')
        self.runFItem.SetBitmap(wx.Bitmap('../resources/Run-icon.png'))
        toolsMenu.AppendItem(self.runFItem)

        self.checkMenu = wx.MenuItem(toolsMenu, 452, '&Check automata')
        self.checkMenu.SetBitmap(wx.Bitmap('../resources/Help-icon.png'))
        toolsMenu.AppendItem(self.checkMenu)

        menuBar.Append(fileMenu, '&File')
        menuBar.Append(toolsMenu, '&Tools')
        self.SetMenuBar(menuBar)

    def CreateBody(self):
        self.mainPanel = wx.Panel(self)
        self.btnPanel = wx.Panel(self)
        self.imgPanel = wx.Panel(self)

        self.btnPanel.SetBackgroundColour(wx.WHITE)
        self.imgPanel.SetBackgroundColour(wx.WHITE)
        self.SetBackgroundColour(wx.WHITE)

        btnSizer = wx.BoxSizer(wx.HORIZONTAL)

        self.newStateButton = wx.Button(self.btnPanel, label='New state')

        self.deleteStateCBox = wx.ComboBox(self.btnPanel, choices=self.names, style=wx.CB_READONLY, size=(50, 40))
        self.deleteStateButton = wx.Button(self.btnPanel, label='Delete state')

        self.fromCBox = wx.ComboBox(self.btnPanel, choices=self.names, style=wx.CB_READONLY, size=(50, 40))
        self.toCBox = wx.ComboBox(self.btnPanel, choices=self.names, style=wx.CB_READONLY, size=(50, 40))
        self.valueInput = wx.TextCtrl(self.btnPanel, -1, size=(40, 25))
        self.drawTransitionButton = wx.Button(self.btnPanel, label='Draw Transition')
        self.deleteTransitionButton = wx.Button(self.btnPanel, label='Delete Transition')

        self.stateEditComboBox = wx.ComboBox(self.btnPanel, choices=self.names, style=wx.CB_READONLY, size=(50, 40))
        self.startCheckBox = wx.CheckBox(self.btnPanel, label='Start state', size=(72, 25))
        self.endCheckBox = wx.CheckBox(self.btnPanel, label='End state', size=(72, 25))

        btnSizer.Add(self.newStateButton, flag=wx.RIGHT, border=20)
        btnSizer.Add(self.deleteStateCBox, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.deleteStateButton, flag=wx.RIGHT, border=20)
        btnSizer.Add(self.fromCBox, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.toCBox, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.valueInput, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.drawTransitionButton, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.deleteTransitionButton, flag=wx.RIGHT, border=20)
        btnSizer.Add(self.stateEditComboBox, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.startCheckBox, flag=wx.RIGHT, border=3)
        btnSizer.Add(self.endCheckBox, flag=wx.RIGHT, border=3)

        self.CreateInitialImage()
        imgSizer = wx.BoxSizer(wx.HORIZONTAL)
        imgSizer.Add(self.graphImage, 1, wx.EXPAND | wx.ALL | wx.ALIGN_CENTER, 5)

        self.btnPanel.SetSizer(btnSizer)
        self.imgPanel.SetSizer(imgSizer)

        mainSizer = wx.BoxSizer(wx.VERTICAL)
        mainSizer.Add(self.btnPanel)
        mainSizer.Add(self.imgPanel, 1, wx.EXPAND, 5)
        self.SetSizer(mainSizer)

    def BindEvents(self):
        self.newStateButton.Bind(wx.EVT_BUTTON, self.OnNewState)
        self.deleteStateButton.Bind(wx.EVT_BUTTON, self.OnDeleteState)
        self.drawTransitionButton.Bind(wx.EVT_BUTTON, self.OnDrawTransition)
        self.deleteTransitionButton.Bind(wx.EVT_BUTTON, self.OnDeleteTransition)
        self.stateEditComboBox.Bind(wx.EVT_COMBOBOX, self.OnStateEditSelect)
        self.startCheckBox.Bind(wx.EVT_CHECKBOX, self.OnEditCheck)
        self.endCheckBox.Bind(wx.EVT_CHECKBOX, self.OnEditCheck)

        self.Bind(wx.EVT_MENU, self.OnNew, self.newFItem)
        self.Bind(wx.EVT_MENU, self.OnSave, self.saveFItem)
        self.Bind(wx.EVT_MENU, self.OnOpen, self.openFItem)
        self.Bind(wx.EVT_MENU, self.OnRun, self.runFItem)
        self.Bind(wx.EVT_MENU, self.OnCheck, self.checkMenu)

        self.mainPanel.Bind(wx.EVT_PAINT, self.OnRepaint)

    def OnCheck(self, event):
        result = self.graph.IsNFA()

        if result:
            wx.MessageBox('Automaton is NFA', 'Info', wx.OK | wx.ICON_INFORMATION)
        else:
            wx.MessageBox('Automaton is DFA', 'Info', wx.OK | wx.ICON_INFORMATION)

    def OnRun(self, event):
        runWindow = InputRunWindow(self.graph, self)

    def OnOpen(self, event):
        openFileDialog = wx.FileDialog(self, "Open", "", "", "obj files (*.obj)|*.obj",
                                       wx.FD_OPEN | wx.FD_FILE_MUST_EXIST)
        openFileDialog.ShowModal()
        path = openFileDialog.GetPath()
        openFileDialog.Destroy()

        file = open(path, 'rb')
        self.graph = pickle.load(file)
        self.UncheckCheckButtons()
        file.close()
        self.Refresh()

    def OnSave(self, event):
        saveFileDialog = wx.FileDialog(self, "Save As", "", "", "obj files (*.obj)|*.obj",
                                       wx.FD_SAVE | wx.FD_OVERWRITE_PROMPT)
        saveFileDialog.ShowModal()
        path = saveFileDialog.GetPath()
        saveFileDialog.Destroy()

        fileHandler = open(path, 'wb')
        pickle.dump(self.graph, fileHandler)
        fileHandler.close()

    def OnNew(self, event):
        self.graph = Graph()
        self.UncheckCheckButtons()
        self.stateIndex = 0
        self.reusableIndexes = []
        self.Refresh()

    def UncheckCheckButtons(self):
        self.startCheckBox.SetValue(False)
        self.endCheckBox.SetValue(False)

    def OnEditCheck(self, event):
        start = self.startCheckBox.IsChecked()
        end = self.endCheckBox.IsChecked()
        self.currentStateName = self.stateEditComboBox.GetValue()
        state = self.graph.FindStateByName(self.currentStateName)
        startState = self.graph.FindStartState()

        if state is None:
            return

        if startState is not None and start is True:
            startState.RemoveStartType()

        state.SetType(start, end)
        self.Refresh()

    def OnStateEditSelect(self, event):
        stateName = event.GetString()
        state = self.graph.FindStateByName(stateName)
        type = state.type

        if type == StateType.NORMAL:
            self.startCheckBox.SetValue(False)
            self.endCheckBox.SetValue(False)
        elif type == StateType.END:
            self.startCheckBox.SetValue(False)
            self.endCheckBox.SetValue(True)
        elif type == StateType.START:
            self.startCheckBox.SetValue(True)
            self.endCheckBox.SetValue(False)
        elif type == StateType.START_END:
            self.startCheckBox.SetValue(True)
            self.endCheckBox.SetValue(True)

    def OnRepaint(self, event):
        digraph = self.generator.GenerateGraphviz(self.graph)
        digraph.render('../resources/fsm')
        image = wx.Image('../resources/fsm.png', wx.BITMAP_TYPE_ANY)
        self.graphImage.Destroy()
        self.graphImage = wx.StaticBitmap(self.imgPanel, wx.ID_ANY, wx.BitmapFromImage(image))
        self.names = []
        for state in self.graph.states:
            self.names.append(state.label)

        self.stateEditComboBox.Clear()
        self.fromCBox.Clear()
        self.toCBox.Clear()
        self.deleteStateCBox.Clear()

        for name in self.names:
            self.stateEditComboBox.Append(name)
            self.fromCBox.Append(name)
            self.toCBox.Append(name)
            self.deleteStateCBox.Append(name)
        self.stateEditComboBox.SetValue(self.currentStateName)

    def OnDeleteTransition(self, event):
        fromValue = self.fromCBox.GetValue()
        toValue = self.toCBox.GetValue()
        self.graph.DeleteTransitionFromNames(fromValue, toValue)
        self.Refresh()

    def OnDrawTransition(self, event):
        fromValue = self.fromCBox.GetValue()
        toValue = self.toCBox.GetValue()
        value = self.valueInput.GetValue()
        self.graph.AddTransitionFromNames(fromValue, toValue, value)
        self.Refresh()

    def OnDeleteState(self, event):
        stateName = self.deleteStateCBox.GetValue()
        result = self.graph.DeleteStateFromName(stateName)

        if result is not None:
            self.reusableIndexes.append(result.index)
            self.reusableIndexes.sort()
        self.Refresh()

    def OnNewState(self, event):
        index = self.stateIndex
        if len(self.reusableIndexes) > 0:
            index = self.reusableIndexes[0]
            del self.reusableIndexes[0]
        else:
            self.stateIndex += 1
        self.graph.AddStateFromName('q' + str(index), index)
        self.Refresh()
