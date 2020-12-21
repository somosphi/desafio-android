import styled from 'styled-components/native';

export const BalanceView = styled.TouchableOpacity`
  height: 92px;
  background: #f8f8f8;
`;

export const StyledBalanceView = styled.View`
  flex-direction: row;
  margin-top: 14px;
`;

export const YourBalanceText = styled.Text`
  font-size: 18px;
  color: #202021;
  margin: 0 16px;
`;

export const BalanceValueNumber = styled.Text`
  color: #00c1af;
  margin-left: 16px;
  font-weight: bold;
  font-size: 24px;
  margin-top: 10px;
`;

export const HideBalanceLine = styled.View`
  background-color: #00c1af;
  width: 170px;
  height: 5px;
  margin-top: 10px;
`;
