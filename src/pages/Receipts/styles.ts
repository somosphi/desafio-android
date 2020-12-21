import styled from 'styled-components/native';

export const StyledSafeAreaView = styled.SafeAreaView`
  background-color: #fff;
  margin-top: 15px;
`;

export const IconView = styled.TouchableOpacity`
  margin: 20px 0 0 16px;
`;

export const ReceiptViewBorder = styled.View`
  margin: 15px;
  border-bottom-color: #000;
  border-bottom-width: 0.75px;
`;

export const ReceiptText = styled.Text`
  font-weight: bold;
  font-size: 18px;
`;

export const HeaderTextView = styled.View`
  align-items: center;
  margin-top: 10px;
`;

export const ReceiptBodyView = styled.View`
  margin: 12px 16px;
`;

export const ReceiptBodyTopText = styled.Text`
  margin: 10px 0;
  font-size: 16px;
  font-weight: bold;
  color: #202021;
`;

export const ReceiptBodyBottomText = styled.Text`
  color: #202021
  font-size: 16px;
`;

export const ShareButton = styled.TouchableOpacity`
  background: #00c1af
  align-items: center;
  justify-content: center;
  margin: 20px;
  height: 50px;
`;

export const ShareButtonText = styled.Text`
  color: #fff;
  font-size: 16px;
`;
