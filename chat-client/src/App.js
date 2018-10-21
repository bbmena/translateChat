import React, { Component } from 'react'
import theme from '@instructure/ui-themes/lib/canvas'
import './App.css';
import Heading from '@instructure/ui-elements/lib/components/Heading'
import GridRow from '@instructure/ui-layout/lib/components/Grid/GridRow/index'
import GridCol from '@instructure/ui-layout/lib/components/Grid/GridCol/index'
import Grid from '@instructure/ui-layout/lib/components/Grid'

import FormFieldGroup from '@instructure/ui-forms/lib/components/FormFieldGroup'
import TextInput from '@instructure/ui-forms/lib/components/TextInput'
import Button from '@instructure/ui-buttons/lib/components/Button'
import Select from '@instructure/ui-forms/lib/components/Select'
import SockJS from 'sockjs-client';
import Stomp from 'stompjs'
import {deleteObjectInArray} from './utils/utils'

import ChatList from './chat-list/chat-list';
import ChatWindow from './chat-window/chat-window';

theme.use();

class App extends Component {

    onConnected(){
        let user = {name: this.state.username, language: this.state.language};
        this.stompClient.subscribe('/users/public', (payload) => {this.onUserAdded(payload, user)});
        this.stompClient.send("/app/user.addUser", {}, JSON.stringify(user));

        this.setState({nameSelected: true})
    };

    onError() {
        console.log("failed to connect web socket")
    };

    onUserAdded(payload, user){
        let message = JSON.parse(payload.body);
        deleteObjectInArray(user, message, 'name');
        this.setState({namesList: message})
    }

    usernameChange(event) {
        this.setState({username: event.target.value})
    }

    languageChange(option) {
        this.setState({language: option.value})
    }

    constructor(){
        super();
        this.connect = this.connect.bind(this);
        this.onConnected = this.onConnected.bind(this);
        this.onError = this.onError.bind(this);
        this.onUserAdded = this.onUserAdded.bind(this);
        this.usernameChange = this.usernameChange.bind(this);
        this.languageChange = this.languageChange.bind(this);
        this.state={
            nameSelected: false,
            username: '',
            language: 'en'
        };
        this.state.title = "Chat List";
        this.state.namesList = [];
    }

    connect() {
        this.socket = new SockJS('/ws');
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect({}, () =>{this.onConnected(this.stompClient)} ,() => {this.onError()});
    }

    render() {
        return (
            <div className="App">

                <Grid>
                    <GridRow hAlign="center">
                        <GridCol><Heading>Chat</Heading></GridCol>
                    </GridRow>
                    {this.state.nameSelected &&
                    <GridRow hAlign="center">
                        <GridCol width={2}>
                            <ChatList namesList={this.state.namesList} title={this.state.title}/>
                        </GridCol>
                        <GridCol width={9}>
                            <ChatWindow stompClient={this.stompClient} language={this.state.language} sender={this.state.username}/>
                        </GridCol>
                    </GridRow>}
                    {!this.state.nameSelected &&
                    <GridRow hAlign="center">
                        <GridCol width={3}>
                            <FormFieldGroup layout="stacked"  description="Select a Username and Language:">
                                <TextInput label="Username" value={this.state.username} onChange={this.usernameChange}/>
                                <Select label="Language" value={this.state.language} onChange={(event, option) =>{this.languageChange(option)}}>
                                    <option value="en">English</option>
                                    <option value="es">Spanish</option>
                                    <option value="ja">Japanese</option>
                                    <option value="fr">French</option>
                                    <option value="de">German</option>
                                </Select>
                                <Button onClick={this.connect}>OK</Button>
                            </FormFieldGroup>
                        </GridCol>
                    </GridRow>}
                </Grid>

            </div>
        )
    }

}

export default App
