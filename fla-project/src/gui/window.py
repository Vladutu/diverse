import wx
from enum import Enum

from src.logic.state import State


class EditorState(Enum):
    SELECT = 0
    DRAW_STATE = 1
    DRAW_TRANSITION = 2
    DELETE = 3


class Window(wx.Frame):
    def __init__(self, parent):
        super(Window, self).__init__(parent, title="FLA project", size=(800, 600))
        self.InitUI()
        self.currentState = EditorState.SELECT
        self.states = []
        self.stateIndex = 0
        self.reusableIndexes = []

    def InitUI(self):
        self.CreateMenu()
        self.CreateBody()
        self.bindEvents()

        self.Center()
        self.Show()

    def CreateMenu(self):
        self.menu = wx.Menu()
        file_item = self.menu.Append(300, 'Start State', kind=wx.ITEM_CHECK)
        # self.Bind(wx.EVT_MENU, self.make_start_state, file_item)

        file_item = self.menu.Append(301, 'End State', kind=wx.ITEM_CHECK)
        # self.Bind(wx.EVT_MENU, self.make_end_state, file_item)

        menuBar = wx.MenuBar()
        fileMenu = wx.Menu()

        newFItem = wx.MenuItem(fileMenu, 1, '&New')
        newFItem.SetBitmap(wx.Bitmap('../resources/Files-New-File-icon.png'))
        fileMenu.AppendItem(newFItem)

        openFItem = wx.MenuItem(fileMenu, 1, '&Open')
        openFItem.SetBitmap(wx.Bitmap('../resources/open-file-icon.png'))
        fileMenu.AppendItem(openFItem)

        saveFItem = wx.MenuItem(fileMenu, 1, '&Save')
        saveFItem.SetBitmap(wx.Bitmap('../resources/save-file-icon.png'))
        fileMenu.AppendItem(saveFItem)

        inputMenu = wx.Menu()
        # runFItem = inputMenu.Append(wx.ID_EXECUTE, 'Run', 'Run input')
        runFItem = wx.MenuItem(inputMenu, 1, '&Run')
        runFItem.SetBitmap(wx.Bitmap('../resources/Run-icon.png'))
        inputMenu.AppendItem(runFItem)

        menuBar.Append(fileMenu, '&File')
        menuBar.Append(inputMenu, '&Input')
        self.SetMenuBar(menuBar)

    def CreateBody(self):
        self.panel = wx.Panel(self)
        self.panel.SetBackgroundColour(wx.WHITE)

        vbox = wx.BoxSizer(wx.VERTICAL)
        hButtonBox = wx.BoxSizer(wx.HORIZONTAL)

        self.selectButton = wx.Button(self.panel, label='Select')
        self.drawStateButton = wx.Button(self.panel, label='Draw State')
        self.drawTransitionButton = wx.Button(self.panel, label='Draw Transition')
        self.deleteButton = wx.Button(self.panel, label='Delete')

        hButtonBox.Add(self.selectButton, flag=wx.RIGHT, border=10)
        hButtonBox.Add(self.drawStateButton, flag=wx.RIGHT, border=10)
        hButtonBox.Add(self.drawTransitionButton, flag=wx.RIGHT, border=10)
        hButtonBox.Add(self.deleteButton, flag=wx.RIGHT, border=10)

        vbox.Add(hButtonBox)
        self.panel.SetSizer(vbox)

    def bindEvents(self):
        self.selectButton.Bind(wx.EVT_BUTTON, self.onSelect)
        self.drawStateButton.Bind(wx.EVT_BUTTON, self.onDrawState)
        self.drawTransitionButton.Bind(wx.EVT_BUTTON, self.onDrawTransition)
        self.deleteButton.Bind(wx.EVT_BUTTON, self.onDelete)

        self.panel.Bind(wx.EVT_LEFT_DOWN, self.onLeftDown)
        self.panel.Bind(wx.EVT_PAINT, self.onPaint)

    def onPaint(self, event):
        dc = wx.PaintDC(self.panel)
        dc.SetPen(wx.Pen(wx.BLACK, 2))
        for state in self.states:
            dc.DrawCircle(state.position.x, state.position.y, state.radius)
            dc.DrawText(state.name, state.position.x, state.position.y)

    def onLeftDown(self, event):
        coordinates = event.GetPosition()
        if self.currentState == EditorState.DRAW_STATE:
            self.onLeftDownAndDrawState(coordinates)
        elif self.currentState == EditorState.DELETE:
            self.onLeftDownAndDelete(coordinates)

    def onLeftDownAndDelete(self, coordinates):
        statesToBeDeleted = []
        for state in self.states:
            if self.mouseIsInCircle(coordinates, state.position, state.radius):
                statesToBeDeleted.append(state)
        for state in statesToBeDeleted:
            self.reusableIndexes.append(state.index)
            self.states.remove(state)
        self.reusableIndexes.sort()
        self.Refresh()

    def onLeftDownAndDrawState(self, coordinates):
        index = self.stateIndex
        if len(self.reusableIndexes) > 0:
            index = self.reusableIndexes[0]
            del self.reusableIndexes[0]
        else:
            self.stateIndex += 1
        state = State(coordinates, index, 'q' + str(index))
        self.states.append(state)
        self.Refresh(False)

    def mouseIsInCircle(self, mousePos, circlePos, circleRad):
        return (mousePos.x - circlePos.x) ** 2 + (mousePos.y - circlePos.y) ** 2 < circleRad ** 2

    def onDrawState(self, event):
        print "Draw state selected"
        self.currentState = EditorState.DRAW_STATE

    def onSelect(self, event):
        print "Select selected"
        self.currentState = EditorState.SELECT

    def onDrawTransition(self, event):
        print "Draw transition selected"
        self.currentState = EditorState.DRAW_TRANSITION

    def onDelete(self, event):
        print "Delete selected"
        self.currentState = EditorState.DELETE
