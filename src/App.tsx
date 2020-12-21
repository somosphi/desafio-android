import React from 'react';
import {StatusBar} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {AreaViewStyle} from './styles';

import Routes from './routes';

const App: React.FC = () => {
  return (
    <NavigationContainer>
      <AreaViewStyle>
        <StatusBar barStyle="dark-content" backgroundColor="#fff" translucent />
        <Routes />
      </AreaViewStyle>
    </NavigationContainer>
  );
};

export default App;
