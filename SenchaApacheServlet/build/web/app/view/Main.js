Ext.define('MyAppServer.view.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'main',
    requires: [
        'Ext.TitleBar',
        'Ext.Video'
    ],
    config: {
        tabBarPosition: 'bottom',
        items: [
            {
                title: 'Insert',
                iconCls: 'home',
                items: [
                    {
                        docked: 'top',
                        xtype: 'titlebar',
                        title: 'Create new Account'
                    },
                    {
                        xtype: 'textfield',
                        label: 'Username: ',
                        id: 'username'
                    },
                    {
                        xtype: 'passwordfield',
                        label: 'Password: ',
                        id: 'password'
                    },
                    {
                        xtype: 'textfield',
                        label: 'Fullname: ',
                        id: 'fullname'
                    },
                    {
                        xtype: 'textfield',
                        label: 'Role: ',
                        id: 'role'
                    }, {
                        xtype: 'button',
                        ui: 'normal',
                        text: 'Insert',
                        handler: function () {
                            {
                                var username = Ext.getCmp('username').getValue();
                                var password = Ext.getCmp('password').getValue();
                                var fullname = Ext.getCmp('fullname').getValue();
                                var role = Ext.getCmp('role').getValue();
                                Ext.Ajax.request(
                                        {
                                            url: './InsertController',
                                            params: {username: username, password: password, fullname: fullname, role: role},
                                            method: 'GET', success: function (response) {
                                                Ext.Msg.alert("successfully inserted " + response.status);
                                            }, failure: function (response) {
                                                Ext.Msg.alert("Failed " + response.status);
                                            }});
                            }
                        }
                    }
                ]
            },
            {
                xtype: 'nestedlist',
                title: 'Filter Account',
                iconCls: 'star',
                id: 'NestedListAllAccount',
                displayField: 'username',
                style: "text-allign:center",
                items: [
                    {
                        xtype: 'toolbar',
                        docked: 'top',
                        layout: 'vbox',
                        items: [
                            {
                                xtype: 'searchfield',
                                placeHolder: 'Fullname...',
                                itemId: 'searchBox',
                                listeners: {
                                    keyup: function (searchfield, e) {
                                        if (e.event.keycode == 13) {
                                            var queryString = searchfield.getValue();
                                            var store = Ext.getStore('StartGetAllAccount');
                                            store.clearFilter();
                                            var dataFilter = "";
                                            if (queryString) {
                                                var thisRegEx = new RegExp(queryString, "i");
                                                store.filterBy(function (record) {
                                                    if (thisRegEx.test(record.get('fullname'))) {
                                                        dataFilter += '{"role":' + record.get('role') +
                                                                '","fullname":"' + record.get('fullname') +
                                                                '","leaf":true, "username":"' + record.get('username') + '"},';
                                                        return true;
                                                    }
                                                    ;
                                                    return false;
                                                });
                                            }
                                            var data = dataFilter.subString(0, dataFilter.length - 1);
                                            data = '[' + data + ']';
                                            var filterResultStore = Ext.create('Ext.data.TreeStore', {
                                                data: data,
                                                fields: [
                                                    {
                                                        name: 'username',
                                                        type: 'string'
                                                    },
                                                    {
                                                        name: 'role',
                                                        type: 'string'
                                                    },
                                                    {
                                                        name: 'fullname',
                                                        type: 'string'
                                                    }
                                                ]
                                            });
                                            Ext.getCmp('NestedListAllAccount').setStore(filterResultStore);
                                            Ext.getCmp('NestedListAllAccount').show();
                                        }
                                    }
                                }
                            }
                        ]
                    }
                ],
                store: {
                    type: 'tree',
                    id: 'StartGetAllAccount',
                    fields: [
                        {
                            name: 'username',
                            type: 'string'
                        },
                        {
                            name: 'fullname',
                            type: 'string'
                        },
                        {
                            name: 'role',
                            type: 'string'
                        }
                    ],
                    proxy: {
                        type: 'ajax',
                        url: './SearchAllController'
                    }
                },
                listConfig: {
                    itemTpl: '<div class="myContent">' +
                            '<div>Username is <b>{username}</b></div>' +
                            '<div>Fullname is <b>{fullname}</b></div>' +
                            '<div>Role is <b>{role}</b></div>' +
                            '</div>',
                    emptyText: '<div class="myContent">No Matching Account</div>'
                },
                detailCard: {
                    xtype: 'panel',
                    scrollable: true,
                    styleHtmlContent: true,
                    layout: 'vbox'
                },
                listeners: {
                    leafitemtap: function (nestedList, list, index, target, record) {
                        var detailCard = nestedList.getDetailCard();
                        detailCard.setHtml('<div><b>You selected: ' + record.get('username') + '</b><div>');
                        Ext.Msg.alert('You clicked row ..', 'The username selected is: ' + record.get('username'));
                        var tabPanel = Ext.create('Ext.Panel', {
                            xtype: 'panel',
                            layout: 'vbox',
                            items: [
                                {
                                    xtype: 'textfield',
                                    label: 'Username: ',
                                    id: 'username',
                                    value: record.get('username')
                                },
                                {
                                    xtype: 'passwordfield',
                                    label: 'Password: ',
                                    id: 'password',
                                },
                                {
                                    xtype: 'textfield',
                                    label: 'Fullname: ',
                                    id: 'fullname',
                                    value: record.get('fullname')
                                },
                                {
                                    xtype: 'textfield',
                                    label: 'Role: ',
                                    id: 'role',
                                    value: record.get('role')
                                },
                                {
                                    xtype: 'button',
                                    ui: 'normal: ',
                                    text: 'Update',
                                    handler: function () {
                                        var username = record.get('username');
                                        var password = Ext.getCmp('password').getValue();
                                        var fullname = Ext.getCmp('fullname').getValue();
                                        var role = Ext.getCmp('role').getValue();
                                        Ext.Ajax.request({
                                            url: './UpdateController',
                                            params: {
                                                username: username, password: password, fullname: fullname, role: role},
                                            method: 'GET', success: function (response) {
                                                Ext.Msg.alert("successfully updated " + response.status);
                                                window.location.reload();
                                            }, failure: function (response) {
                                                Ext.Msg.alert("Failed " + response.status);
                                            }});
                                    }
                                },
                                {
                                    xtype: 'button',
                                    ui: 'normal: ',
                                    text: 'Delete',
                                    handler: function () {
                                        var username = record.get('username');
                                        Ext.Ajax.request({
                                            url: './DeleteController', params: {
                                                username: username
                                            },
                                            method: 'GET', success: function () {
                                                window.location.reload();
                                            }, failure: function (response) {
                                                Ext.Msg.alert("Failed " + response.status);
                                            }});
                                    }
                                }
                            ]
                        });
                        detailCard.add(tabPanel);
                    }
                }
            },
            {
                xtype: 'nestedlist',
                title: 'Search Account',
                iconCls: 'action',
                id: 'NestedListSearchAccount',
                displayField: 'username',
                style: "text-allign:center",
                items: [{
                        xtype: 'toolbar',
                        docked: 'top',
                        items: [
                            {
                                xtype: 'searchfield',
                                placeholder: 'Search',
                                itemId: 'searchBox',
                                listeners: {
                                    keyup: function (searchfield, e) {
                                        if (e.event.keycode == 13) {
                                            queryString = searchfield.getValue();
                                            var SearchStore = Ext.create('Ext.data.TreeStore',
                                                    {
                                                        fields: [
                                                            {
                                                                name: 'username',
                                                                type: 'string'
                                                            },
                                                            {
                                                                name: 'role',
                                                                type: 'string'
                                                            },
                                                            {
                                                                name: 'fullname',
                                                                type: 'string'
                                                            }
                                                        ],
                                                        proxy: {
                                                            type: 'ajax',
                                                            url: 'SearchController?search=' + queryString
                                                        }
                                                    });
                                            Ext.getCmp("NestedListSearchAccount").setStore(SearchStore);
                                            Ext.getCmp("NestedListSearchAccount").show();
                                        }
                                    }
                                }
                            }
                        ]
                    }
                ],
                listCongif: {

                },
                detailCard: {

                },
                listeners: {

                }
            }
        ]
    }
});
