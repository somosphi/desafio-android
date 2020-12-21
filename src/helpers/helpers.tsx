export function formatDate(date: string, small?: boolean) {
  if (small) {
    const slicedDate = date.split(/-|T/);
    const formatedDate = `${slicedDate[2]}/${slicedDate[1]}`;
    return formatedDate;
  }

  const slicedFullDate = date.split(/-|T|Z/);
  const formatedDate = `${slicedFullDate[2]}/${slicedFullDate[1]}/${slicedFullDate[0]} - ${slicedFullDate[3]}`;
  return formatedDate;
}

export function formatValue(value: number) {
  return value
    .toFixed(2)
    .replace('.', ',')
    .replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
}
